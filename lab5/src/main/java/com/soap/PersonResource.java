package com.soap;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/persons")
@Produces({MediaType.APPLICATION_JSON})

public class PersonResource {

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
    public String createPerson(
            @QueryParam("personName") String name,
            @QueryParam("personPatronymic") String patronymic,
            @QueryParam("personSurname") String surname,
            @QueryParam("personAge") String age,
            @QueryParam("personGender") String gender) {
        return new PostgreSQLDAO().createPerson(name, patronymic, surname, Integer.parseInt(age), gender);
    }

    @DELETE
    public String deletePerson(@QueryParam("id") String person_id) {
        return new PostgreSQLDAO().deletePerson(Integer.parseInt(person_id));
    }

    @PUT
    public String updatePerson(
            @QueryParam("id") String person_id,
            @QueryParam("personName") String name,
            @QueryParam("personPatronymic") String patronymic,
            @QueryParam("personSurname") String surname,
            @QueryParam("personAge") String age,
            @QueryParam("personGender") String gender) {

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