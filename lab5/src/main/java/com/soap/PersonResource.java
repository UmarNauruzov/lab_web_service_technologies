package com.soap;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.StringTokenizer;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import com.soap.error.*;
import org.postgresql.util.Base64;

import javax.ws.rs.core.HttpHeaders;

@Path("/persons")
@Produces({MediaType.APPLICATION_JSON})

public class PersonResource {

    private static final String AUTH_HEADER_KEY = "Authorization";
    private static final String AUTH_HEADER_PREFIX = "Basic ";

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    @GET
    public LinkedHashSet<Person> getPersons(@QueryParam("query") String query, @QueryParam("searchParam") final List<String> searchArgs) {
        if (query != null) {
            // Обработать случай, когда присутствует параметр запроса
            System.out.println("Вывод всех людей" + query);
            return new PostgreSQLDAO().getPersons();
        } else if (searchArgs != null) {
            // Обработать случай, когда присутствует параметр searchParam
            System.out.println("Search parameters: " + searchArgs);
            return new PostgreSQLDAO().getPersonsFields(searchArgs);
        } else {
            // Обработать случай, когда нет ни query, ни searchParam
            throw new IllegalArgumentException("Должен быть указан параметр 'query' или 'searchParam'.");
        }
    }

    @POST
    public String createPerson(@Context HttpHeaders headers,
            @QueryParam("personName") String name,
            @QueryParam("personPatronymic") String patronymic,
            @QueryParam("personSurname") String surname,
            @QueryParam("personAge") String age,
            @QueryParam("personGender") String gender) throws AuthException{
        if (!isAuthenticated(headers)) {
            throw AuthException.DEFAULT_INSTANCE;
        } else {
            return new PostgreSQLDAO().createPerson(name, patronymic, surname, Integer.parseInt(age), gender);
        }
    }

    @DELETE
    public String deletePerson(@Context HttpHeaders headers, @QueryParam("id") String person_id) throws AuthException {
        if (!isAuthenticated(headers)) {
            throw AuthException.DEFAULT_INSTANCE;
        } else {
            return new PostgreSQLDAO().deletePerson(Integer.parseInt(person_id));
        }
    }

    @PUT
    public String updatePerson(@Context HttpHeaders headers,
            @QueryParam("id") String person_id,
            @QueryParam("personName") String name,
            @QueryParam("personPatronymic") String patronymic,
            @QueryParam("personSurname") String surname,
            @QueryParam("personAge") String age,
            @QueryParam("personGender") String gender) throws AuthException {
        if (!isAuthenticated(headers)) {
            throw AuthException.DEFAULT_INSTANCE;
        } else {
            List<String> updateArgs = new ArrayList<>();

            if (name != null && !name.trim().isEmpty()) updateArgs.add("name = '" + name + "'");
            if (patronymic != null && !patronymic.trim().isEmpty())
                updateArgs.add("patronymic = '" + patronymic + "'");
            if (surname != null && !surname.trim().isEmpty())
                updateArgs.add("surname = '" + surname + "'");
            if (age != null && !age.trim().isEmpty()) updateArgs.add("age = '" + age + "'");
            if (gender != null && !gender.trim().isEmpty()) updateArgs.add("gender = '" + gender + "'");

            return new PostgreSQLDAO().updatePerson(Integer.parseInt(person_id), updateArgs);
        }
    }

    private boolean isAuthenticated(HttpHeaders headers) {
        List<String> authHeader = headers.getRequestHeaders().get(AUTH_HEADER_KEY);
        if (authHeader == null) {
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