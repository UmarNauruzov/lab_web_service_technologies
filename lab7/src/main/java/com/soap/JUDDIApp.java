package com.soap;

import com.soap.client.WebServiceClient;
import org.apache.juddi.api_v3.AccessPointType;
import org.apache.juddi.v3.client.UDDIConstants;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.apache.juddi.v3.client.transport.Transport;
import org.uddi.api_v3.*;
import org.uddi.v3_service.UDDIInquiryPortType;
import org.uddi.v3_service.UDDIPublicationPortType;
import org.uddi.v3_service.UDDISecurityPortType;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JUDDIApp {
    private static UDDISecurityPortType security = null;
    private static UDDIInquiryPortType inquiry = null;
    private static UDDIPublicationPortType publish = null;

    public static void main(String[] args) {

        // Получить данные из консоли
        Scanner scanner = new Scanner(System.in);

        System.out.println("Пожалуйста, введите свое имя пользователя администратора uddi (default: uddiadmin)?");
        String userName = scanner.nextLine();
        if (userName.trim().isEmpty()) {
            userName = "uddiadmin";
        }
        System.out.println("Пожалуйста, введите свой пароль администратора jUDDI (default: da_password1)?");
        String userPass = scanner.nextLine();
        if (userPass.trim().isEmpty()) {
            userPass = "da_password1";
        }

        // Создайте UDDI-клиент и прокси-сервер для настройки, добавьте ссылки на UDDI API
        JUDDIApp app = new JUDDIApp();
        // Получить токен аутентификации в виде строки
        String token = app.getUDDIToken(userName, userPass);

        // Зарегистрируйте новую услугу (для jUDDI версии 3.0 и выше)
        System.out.println("Вы хотите зарегистрировать новый бизнес/услугу? (y -> yes, other -> no)");
        String agree = scanner.nextLine();
        if (agree.equals("y")) {

            System.out.println("Какое фирменное наименование jUDDI мы будем использовать (default: CRUDCompane)?");
            String businessName = scanner.nextLine();
            if (businessName.trim().isEmpty()) {
                businessName = "CRUDCompane";
            }

            System.out.println("Какое имя службы jUDDI мы будем использовать (default: CRUDService)?");
            String registeredServiceName = scanner.nextLine();
            if (registeredServiceName.trim().isEmpty()) {
                registeredServiceName = "CRUDService";
            }

            System.out.println("Какая точка доступа к сервису jUDDI (default: http://localhost:8090/PersonServiceCRUD?wsdl)?");
            String registeredServiceURL = scanner.nextLine();
            if (registeredServiceURL.trim().isEmpty()) {
                registeredServiceURL = "http://localhost:8090/PersonServiceCRUD?wsdl";
            }
            app.registerNewService(token, businessName, registeredServiceName, registeredServiceURL);
        }

        System.out.println("Вы хотите выполнить поиск и запросить какой-нибудь сервис? (y -> yes, other -> no)");
        agree = scanner.nextLine();
        if (agree.equals("y")) {

            System.out.println("Какое название сервиса jUDDI мы будем искать (default: CRUDService)?");
            String searchServiceName = scanner.nextLine();
            if (searchServiceName.trim().isEmpty()) {
                searchServiceName = "CRUDService";
            }

            // Поиск сервиса
            String accessPoint;
            try {
                accessPoint = app.searchService(app.GetBusinessList(inquiry, token).getBusinessInfos(), inquiry, token, searchServiceName);
                System.out.println("Вы хотите запросить этот сервис прямо сейчас? (y -> yes, other -> no)");
                agree = scanner.nextLine();
                if (agree.equals("y")) {
                    WebServiceClient serviceClient = new WebServiceClient();
                    serviceClient.serviceCRUD(accessPoint);
                }
            } catch (Exception ex) {
                Logger.getLogger(JUDDIApp.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        }

        try {
            security.discardAuthToken(new DiscardAuthToken(token));
        } catch (RemoteException ex) {
            Logger.getLogger(JUDDIApp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            scanner.close();
        }

    }


    public JUDDIApp() {
        try {
            // создаем UDDI-клиент и прокси-сервер для настройки
            UDDIClient client = new UDDIClient("META-INF/service_search.xml");
            // Клиент UDDI может быть клиентом нескольких узлов UDDI, поэтому
            // укажите имя узла, определенное в service_search.xml.
            // Транспортировка также определена в service_search.xml.
            Transport transport = client.getTransport("default");
            // Теперь создаем ссылку на UDDI API
            security = transport.getUDDISecurityService();
            inquiry = transport.getUDDIInquiryService();
            publish = transport.getUDDIPublishService();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getUDDIToken(String jUDDIUserName, String jUDDIUSerPass) {
        String token = null;
        // Получить токен аутентификации (добавляет учетные данные к прокси-серверам ws)
        GetAuthToken getAuthToken = new GetAuthToken();
        getAuthToken.setUserID(jUDDIUserName);
        getAuthToken.setCred(jUDDIUSerPass);
        // Выполнение вызова API, который извлекает токен аутентификации для пользователя.
        try {
            AuthToken authToken = security.getAuthToken(getAuthToken);
            token =  authToken.getAuthInfo();
        } catch (RemoteException ex) {
            Logger.getLogger(JUDDIApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return token;
    }

    /**
     * Зарегистрируем новый сервис в jUDDI с помощью WSDL
     * на основе правил контроля доступа
     */
    private void registerNewService(
            String token,
            String businessName,
            String registeredServiceName,
            String registeredServiceURL) {
        // Создание родительского бизнес-объекта, который будет содержать наш сервис.
        BusinessEntity myBusEntity = new BusinessEntity();
        Name myBusName = new Name();
        myBusName.setValue(businessName);
        myBusEntity.getName().add(myBusName);

        // Добавление бизнес-объекта в структуру "сохранить", используя наш
        // аутентификационную информацию издателя и сохраняем ее.
        SaveBusiness sb = new SaveBusiness();
        sb.getBusinessEntity().add(myBusEntity);
        sb.setAuthInfo(token);

        try {
            BusinessDetail bd = publish.saveBusiness(sb);
            String myBusKey = bd.getBusinessEntity().get(0).getBusinessKey();

            // Создаем службу для сохранения.
            // Только добавление минимальных данных: извлечен родительский бизнес-ключ
            // от спасения бизнеса
            // выше и одно-единственное имя.
            BusinessService myService = new BusinessService();
            myService.setBusinessKey(myBusKey);
            Name myServName = new Name();
            myServName.setValue(registeredServiceName);
            myService.getName().add(myServName);

            // Добавляйем шаблоны привязки и т.д..
            BindingTemplate myBindingTemplate = new BindingTemplate();
            AccessPoint accessPoint = new AccessPoint();
            accessPoint.setUseType(AccessPointType.WSDL_DEPLOYMENT.toString());
            accessPoint.setValue(registeredServiceURL);
            myBindingTemplate.setAccessPoint(accessPoint);
            BindingTemplates myBindingTemplates = new BindingTemplates();
            // необязательный, но рекомендуемый шаг, это аннотирует нашу привязку ко всем
            // стандартная информация об экземпляре SOAP tModel
            myBindingTemplate = UDDIClient.addSOAPtModels(myBindingTemplate);
            myBindingTemplates.getBindingTemplate().add(myBindingTemplate);

            myService.setBindingTemplates(myBindingTemplates);

            // Добавление сервиса в структуру "сохранить" с помощью нашего издателя
            // аутентификационная информация и ее сохранение.
            SaveService ss = new SaveService();
            ss.getBusinessService().add(myService);
            ss.setAuthInfo(token);
            ServiceDetail sd = publish.saveService(ss);
            String myServKey = sd.getBusinessService().get(0).getServiceKey();
            System.out.println("myService key:  " + myServKey);

            // Теперь вы опубликовали бизнес и услугу с помощью jUDDI API!
            System.out.println("New service successfully registered!");

        } catch (RemoteException ex) {
            Logger.getLogger(JUDDIApp.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    /**
     * Находим все зарегистрированные предприятия. Этот список можно отфильтровать
     * на основе правил контроля доступа
     */
    private BusinessList GetBusinessList(
            UDDIInquiryPortType inquiry,
            String token)
            throws Exception {
        FindBusiness fb = new FindBusiness();
        fb.setAuthInfo(token);
        org.uddi.api_v3.FindQualifiers fq = new org.uddi.api_v3.FindQualifiers();
        fq.getFindQualifier().add(UDDIConstants.APPROXIMATE_MATCH);
        fb.setFindQualifiers(fq);
        Name searchName = new Name();
        searchName.setValue(UDDIConstants.WILDCARD);
        fb.getName().add(searchName);

        return inquiry.findBusiness(fb);
    }

    /**
     * Находим зарегистрированный сервис с каким-нибудь названием.
     */
    private String searchService(
            BusinessInfos businessInfos,
            UDDIInquiryPortType inquiry,
            String token,
            String serviceName)
            throws Exception {

        for (int i = 0; i < businessInfos.getBusinessInfo().size(); i++) {
            GetServiceDetail gsd = new GetServiceDetail();
            // Перехватываем NullPointerException, чтобы не выводить в лог.
            // Данное исключение лишь показывает, что закончились сервисы и мы получили null
            try {
                for (int k = 0; k < businessInfos.getBusinessInfo().get(i).getServiceInfos().getServiceInfo().size(); k++) {
                    gsd.getServiceKey().add(businessInfos.getBusinessInfo().get(i).getServiceInfos().getServiceInfo().get(k).getServiceKey());
                }
                gsd.setAuthInfo(token);
                ServiceDetail serviceDetail = inquiry.getServiceDetail(gsd);
                for (int k = 0; k < serviceDetail.getBusinessService().size(); k++) {
                    BusinessService get = serviceDetail.getBusinessService().get(k);

                    if (ListToString(get.getName()).equals(serviceName)) {
                        System.out.println("Точка доступа к сервису выборки для бизнеса " + businessInfos.getBusinessInfo().get(i).getBusinessKey());
                        System.out.println("Мы находим эту услугу в jUDDI register!");
                        return  getServiceAccessPoint(get.getBindingTemplates());
                    }
                }
            } catch (NullPointerException ex) {
                System.out.println("Вот и все! Мы получаем " + ex);
                return null;
            }
        }
        return null;
    }

    /**
     * Эта функция преобразует сложный формат данных UDDI в строку с точкой доступа к сервису
     */
    private String getServiceAccessPoint(BindingTemplates bindingTemplates) {
        if (bindingTemplates == null) {
            return null;
        }
        String serviceAccessPoint = null;
        for (int i = 0; i < bindingTemplates.getBindingTemplate().size(); i++) {
            if (bindingTemplates.getBindingTemplate().get(i).getAccessPoint() != null) {
                if (bindingTemplates.getBindingTemplate().get(i).getAccessPoint().getUseType() != null) {
                    if (bindingTemplates.getBindingTemplate().get(i).getAccessPoint().getUseType().equalsIgnoreCase(AccessPointType.WSDL_DEPLOYMENT.toString())) {
                        serviceAccessPoint = bindingTemplates.getBindingTemplate().get(i).getAccessPoint().getValue();
                    }
                }
            }
        }
        return serviceAccessPoint;
    }

    /**
     * Эта функция преобразует данные списка в строку
     */
    private String ListToString(List<Name> name) {
        StringBuilder sb = new StringBuilder();
        for (Name value : name) {
            sb.append(value.getValue());
        }
        return sb.toString();
    }

}