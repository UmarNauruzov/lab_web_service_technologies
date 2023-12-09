package com.soap.client;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.postgresql.util.Base64;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;


public class WebServiceClient {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    public static void main(String[] args) throws MalformedURLException, AuthException {
        URL url = new URL("http://localhost:8080/PersonServiceCRUD?wsdl");
        PersonServiceCRUD personService = new PersonServiceCRUD(url);

        // Выбор метода CRUD
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите метод CRUD (введите CREATE, READ, UPDATE или DELETE) или введите \"exit\" для выхода:");
        String choosenMethod;
        do {
            choosenMethod = scanner.nextLine();
            // проверяем строку на наличие аргумента: если строка не является пустой и не состоит из пробелов, то
            // проверяем на наличие одной из возможных операций CRUD
            if (choosenMethod != null && !choosenMethod.trim().isEmpty()) {

                switch (choosenMethod) {
                    case ("CREATE"):
                        createPersonMethod(personService);
                        System.out.println("Вот и все! Вы можете выбрать другой метод CRUD или ввести \"exit\" для выхода\"");
                        break;
                    case ("READ"):
                        readPersonMethod(personService);
                        System.out.println("Вот и все! Вы можете выбрать другой метод CRUD или ввести \"exit\" для выхода\"");
                        break;
                    case ("UPDATE"):
                        updatePersonMethod(personService);
                        System.out.println("Вот и все! Вы можете выбрать другой метод CRUD или ввести \"exit\" для выхода\"");
                        break;
                    case ("DELETE"):
                        deletePersonMethod(personService);
                        System.out.println("Вот и все! Вы можете выбрать другой метод CRUD или ввести \"exit\" для выхода\"");
                        break;
                    case ("exit"):
                        System.out.println("Bye-Bye!");
                        break;
                    default:
                        System.out.println("Вы можете ввести просто CREATE, READ, UPDATE или DELETE!");
                        System.out.println("Повторите попытку или используйте \"exit\" для выхода.");
                        break;
                }
            }
        } while (!Objects.equals(choosenMethod, "exit"));
        scanner.close();
    }

    private static void updatePersonMethod(PersonServiceCRUD personService) throws AuthException {
        String authToken = getAuthToken();
        System.out.println(authToken);
        String status = "Bad";

        // Консольный ввод аргументов
        Scanner scanner = new Scanner(System.in);

        System.out.print("Input personID (integer): ");
        String PersonIDString = scanner.nextLine();
        // Проверяем personId на число
        int personId = -1;
        if (PersonIDString != null && !PersonIDString.trim().isEmpty()) {
            try {
                personId = Integer.parseInt(PersonIDString.trim());
            } catch (NumberFormatException ex) {
                Logger.getLogger(WebServiceClient.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Ваш запрос неверен! PersonId является целым числом.");
            }
        }

        if (personId != -1) {

            System.out.println("Какие поля вы хотите обновить для этой строки? \n" +
                    "Выберите поля \"name\", \"patronymic\", \"surname\", \"age\", \"gender\" и введите их ниже \n" +
                    " разделяется запятой без пробелов");
            String updateRows = scanner.nextLine();

            // Преобразуем полученную строку в список аргументов
            String[] updateRowsList = updateRows.split(",", -1);

            Map<String, String> updateRowsMap = new HashMap<>();
            updateRowsMap.put("name", "");
            updateRowsMap.put("patronymic", "");
            updateRowsMap.put("surname", "");
            updateRowsMap.put("age", "");
            updateRowsMap.put("gender", "");

            /*
            Проходим по списку аргументов и сопоставляем их с наименованиями полей таблицы в базе данных.
            В данном контексте, нет необходимости проверять наличие пустых строк или строк, состоящих только
            из пробелов, так как в таких ситуациях код автоматически перейдет к выполнению операции по умолчанию
            в блоке switch-case, как и в случае отсутствия совпадений с шаблоном.
            Если обнаружено совпадение, запрашивается ввод соответствующего значения, после чего происходит
            проверка на пустую строку. Значения добавляются в массив, который служит основой для формирования запроса.
            Если в результате обработки массив остается пустым (то есть ни одно из значений не подходит),
            выводится сообщение о том, что введен запрос с ошибкой. Помимо этого,
            целочисленное значение age подвергается дополнительной проверке на соответствие числовому формату (int).
            */
            for (String row : updateRowsList) {
                switch (row) {
                    case "name":
                        System.out.println("Введите новое значение для поля \"name\":");
                        String name = scanner.nextLine();
                        if (name != null && !name.trim().isEmpty()) {
                            updateRowsMap.put("name", name);
                        } else {
                            System.out.println("Поле \"name\" неверно и обновляться не будет!");
                        }
                        break;
                    case "patronymic":
                        System.out.println("Введите новое значение для поля \"patronymic\":");
                        String patronymic = scanner.nextLine();
                        if (patronymic != null && !patronymic.trim().isEmpty()) {
                            updateRowsMap.put("patronymic", patronymic);
                        } else {
                            System.out.println("Поле \"patronymic\" неверно и обновляться не будет!");
                        }
                        break;

                    case "surname":
                        System.out.println("Введите новое значение для поля \"surname\":");
                        String surname = scanner.nextLine();
                        if (surname != null && !surname.trim().isEmpty()) {
                            updateRowsMap.put("surname", surname);
                        } else {
                            System.out.println("Поле \"surname\" неверно и обновляться не будет!");
                        }
                        break;

                    case "age":
                        System.out.println("Введите новое значение для поля \"age\" (целое число):");
                        String age = scanner.nextLine();
                        try {
                            Integer.parseInt(age.trim());
                        } catch (NumberFormatException ex) {
                            Logger.getLogger(WebServiceClient.class.getName()).log(Level.SEVERE, null, ex);
                            age = "";
                        }
                        if (!age.trim().isEmpty()) {
                            updateRowsMap.put("age", age);
                        } else {
                            System.out.println("Поле \"age\" не является целым числом и обновляться не будет!");
                        }
                        break;

                    case "gender":
                        System.out.println("Введите новое значение для поля \"gender\":");
                        String gender = scanner.nextLine();
                        if (gender != null && !gender.trim().isEmpty()) {
                            updateRowsMap.put("gender", gender);
                        } else {
                            System.out.println("Поле \"gender\" неверно и обновляться не будет!");
                        }
                        break;

                    default:
                        System.out.println("Неверный запрос. Попробуйте еще раз!");
                        break;
                }
            }

            int i = 0;
            for(String val : updateRowsMap.values()){
                if (val != null && !val.trim().isEmpty()) {
                    i++;
                }
            }

            if (i != 0) {

                System.out.println("Вы вводите новые значения для строки " + personId + ":\n" + updateRowsMap);
                System.out.println("Вы действительно хотите изменить эти поля для строки " + personId + "? (y -> yes, other -> no)");
                String agree = scanner.nextLine();
                if (agree.equals("y")) {
                    // Добавление заголовка Authorization
                    Map<String, List<String>> requestHeaders = new HashMap<>();
                    requestHeaders.put("Authorization", Collections.singletonList(authToken));
                    ((BindingProvider) personService.getPersonWebServicePort()).getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, requestHeaders);

                    String name = updateRowsMap.get("name");
                    String patronymic = updateRowsMap.get("patronymic");
                    String surname = updateRowsMap.get("surname");
                    String ageStr = updateRowsMap.get("age").trim();
                    int age = ageStr.isEmpty() ? 0 : Integer.parseInt(ageStr);
                    String gender = updateRowsMap.get("gender");

                    status = personService.getPersonWebServicePort().updatePerson(personId, name, patronymic, surname, age, gender);

                } else {
                    System.out.println("Вы просто отменяете свой запрос. Попробуйте другой запрос или выйдите.");
                }



            } else {
                System.out.println("Все аргументы пусты. Обновление строки не может быть завершено.");
            }
        }
        System.out.println("Status: " + status);
    }


    private static void deletePersonMethod(PersonServiceCRUD personService) {
        String authToken = getAuthToken();
        System.out.println(authToken);
        String status = "Bad";

        // Консольный ввод аргументов
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите идентификатор пользователя (целое число): ");
        String personIDString = scanner.nextLine();


        try {
            int personId = Integer.parseInt(personIDString.trim());
            // Добавление заголовка Authorization
            Map<String, List<String>> requestHeaders = new HashMap<>();
            requestHeaders.put("Authorization", Collections.singletonList(authToken));
            ((BindingProvider) personService.getPersonWebServicePort()).getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, requestHeaders);

            status = personService.getPersonWebServicePort().deletePerson(personId);

            if (status.equals("1")) status = "Good";
            System.out.println("Status: " + status);
        } catch (NumberFormatException ex) {
            System.out.println("Неверное значение personId! Введите только одно целое число..");
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
    }


    private static void readPersonMethod(PersonServiceCRUD personService) {
        List<Person> persons = personService.getPersonWebServicePort().getPersons();
        for (Person person : persons) {
            System.out.println("id: " + person.getPersonId() + " name: " + person.getName() + ", surname: " + person.getSurname() + ", age: " +
                    person.getAge());
        }
    }

    private static void createPersonMethod(PersonServiceCRUD personService) {
        String authToken = getAuthToken();

        String status = "Bad";

        // Консольный ввод аргументов
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите отчество: ");
        String patronymic = scanner.nextLine();
        System.out.print("Введите фамилию: ");
        String surname = scanner.nextLine();
        System.out.print("Введите возраст (число): ");
        String ageString = scanner.nextLine();
        System.out.print("Введите пол: ");
        String gender = scanner.nextLine();

        int age;

        // проверим ввод на наличие значений: строка не является пустой и не состоит из пробелов
        if ((name != null && !name.trim().isEmpty())  &&
                (patronymic != null && !patronymic.trim().isEmpty())  &&
                (surname != null && !surname.trim().isEmpty()) &&
                (ageString != null && !ageString.trim().isEmpty()) &&
                (gender != null && !gender.trim().isEmpty())) {
            // Добавление заголовка Authorization
            Map<String, List<String>> requestHeaders = new HashMap<>();
            requestHeaders.put("Authorization", Collections.singletonList(authToken));
            ((BindingProvider) personService.getPersonWebServicePort()).getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, requestHeaders);

            try {
                age = Integer.parseInt(ageString.trim());
                status = personService.getPersonWebServicePort().createPerson(name, patronymic, surname, age, gender);
                if (status.equals("1")) status = "Good";
                System.out.println("Status: " + status);
            } catch (NumberFormatException ex) {
                System.out.println("Неверный возраст!");
            } catch (AuthException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println("Ваш запрос неверен!");
        }
    }
    private static String getAuthToken() {
        return "Basic " + Base64.encodeBytes((USERNAME + ":" + PASSWORD).getBytes());
    }
}