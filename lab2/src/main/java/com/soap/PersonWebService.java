package com.soap;

import javax.annotation.Resource;
import javax.xml.ws.handler.MessageContext;
import com.soap.error.*;
import org.postgresql.util.Base64;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;
import javax.xml.ws.WebServiceContext;

@WebService(serviceName = "PersonServiceCRUD")
public class PersonWebService {

    private static final String AUTH_HEADER_KEY = "Authorization";
    private static final String AUTH_HEADER_PREFIX = "Basic ";

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    @Resource
    private
    WebServiceContext webServiceContext;

    @WebMethod(operationName = "getPersons")
    public List<Person> getPersons() {
        PostgreSQLDAO dao = new PostgreSQLDAO();
        List<Person> persons = dao.getPersons();
        return persons;
    }
    @WebMethod(operationName = "createPerson")
    public String createPerson(@WebParam(name = "personName") String name,
                                @WebParam(name = "personPatronymic") String patronymic,
                                @WebParam(name = "personSurname") String surname,
                                @WebParam(name = "personAge") int age,
                                @WebParam(name = "personGender") String gender) throws AuthException {
        MessageContext messageContext = webServiceContext.getMessageContext();
        if (!isAuth(messageContext))
        {
            AuthFault fault = AuthFault.defaultInstance();
            throw new AuthException("Invalid login-password", fault);
        } else {
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.createPerson(name, patronymic, surname, age, gender);
        }
    }

    @WebMethod(operationName = "deletePerson")
    public String deletePerson(@WebParam(name = "person_id") int person_id) throws AuthException {
        MessageContext messageContext = webServiceContext.getMessageContext();
        if (!isAuth(messageContext))
        {
            AuthFault fault = AuthFault.defaultInstance();
            throw new AuthException("Invalid login-password", fault);
        } else {
            PostgreSQLDAO dao = new PostgreSQLDAO();
            return dao.deletePerson(person_id);
        }
    }

    @WebMethod(operationName = "updatePerson")
    public String updatePerson(@WebParam(name = "person_id") int person_id,
                               @WebParam(name = "personName") String name,
                               @WebParam(name = "personPatronymic") String patronymic,
                               @WebParam(name = "personSurname") String surname,
                               @WebParam(name = "personAge") int age,
                               @WebParam(name = "personGender") String gender) throws AuthException {
        MessageContext messageContext = webServiceContext.getMessageContext();
        if (!isAuth(messageContext))
        {
            AuthFault fault = AuthFault.defaultInstance();
            throw new AuthException("Invalid login-password", fault);
        } else {
            List<String> updateArgs = new ArrayList<>();

            if (name != null && !name.trim().isEmpty()) updateArgs.add("name = '" + name + "'");
            if (patronymic != null && !patronymic.trim().isEmpty()) updateArgs.add("patronymic = '" + patronymic + "'");
            if (surname != null && !surname.trim().isEmpty()) updateArgs.add("surname = '" + surname + "'");
            if (age != 0) updateArgs.add("age = '" + age + "'");
            if (gender != null && !gender.trim().isEmpty()) updateArgs.add("gender = '" + gender + "'");


            PostgreSQLDAO dao = new PostgreSQLDAO();
            return dao.updatePerson(person_id, updateArgs);
        }
    }
    private boolean isAuth(MessageContext ctx) {
        Map headers = (Map) ctx.get(MessageContext.HTTP_REQUEST_HEADERS);
        if (headers == null || !headers.containsKey(AUTH_HEADER_KEY)) {
            return false;
        }

        List<String> authHeader = (List<String>) headers.get(AUTH_HEADER_KEY);
        if (authHeader == null || authHeader.isEmpty()) {
            return false;
        }

        String authToken = authHeader.get(0);
        if (authToken.isEmpty()) {
            return false;
        }

        authToken = authToken.replaceFirst(AUTH_HEADER_PREFIX, "");
        String decodedString = new String(Base64.decode(authToken));
        StringTokenizer stringTokenizer = new StringTokenizer(decodedString, ":");
        String username = stringTokenizer.nextToken();
        String password = stringTokenizer.nextToken();

        return username.equals(USERNAME) && password.equals(PASSWORD);
    }
}