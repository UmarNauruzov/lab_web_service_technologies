package com.soap;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.soap.error.*;

@Path("/persons")
@Produces({MediaType.APPLICATION_JSON})

public class PersonResource {

    @GET
    public LinkedHashSet<Person> getPersons(@QueryParam("query") String query, @QueryParam("searchParam") final List<String> searchArgs) throws EmptyFieldException {
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
            throw new EmptyFieldException("Должен быть указан параметр 'query' или 'searchParam'.");
        }
    }

    @POST
    public String createPerson(
            @QueryParam("personName") String name,
            @QueryParam("personPatronymic") String patronymic,
            @QueryParam("personSurname") String surname,
            @QueryParam("personAge") String age,
            @QueryParam("personGender") String gender)
            throws EmptyFieldException, CastIntException, MarkFieldException {

        String status = "-1";

        if (name != null && !name.trim().isEmpty() &&
                patronymic != null && !patronymic.trim().isEmpty() &&
                surname != null && !surname.trim().isEmpty() &&
                age != null && !age.trim().isEmpty() &&
                gender != null && !gender.trim().isEmpty()) {

            try {
                Integer.parseInt(age.trim());

                if (gender.equals("мужской") ||
                        gender.equals("женский")) {

                    status = new PostgreSQLDAO().createPerson(name, patronymic, surname, Integer.parseInt(age), gender);

                } else {
                    throw MarkFieldException.DEFAULT_INSTANCE;
                }

            } catch (NumberFormatException ex) {
                throw new CastIntException("Произошла ошибка при попытке преобразовать 'age' в целые числа.");
            }

        } else {
            throw EmptyFieldException.DEFAULT_INSTANCE;
        }

        return status;

    }

    @DELETE
    public String deletePerson(@QueryParam("id") String person_id)
            throws EmptyFieldException, RowNotExistsException, CastIntException {
        String status;
        if (person_id != null && !person_id.trim().isEmpty()) {
            try {
                status = new PostgreSQLDAO().deletePerson(Integer.parseInt(person_id));
                if (status.equals("0")) {
                    throw RowNotExistsException.DEFAULT_INSTANCE;
                }
            } catch (NumberFormatException ex) {
                throw new CastIntException("Произошла ошибка при попытке преобразовать 'id' в целое число.");
            }
        } else {
            throw EmptyFieldException.DEFAULT_INSTANCE;
        }
        return status;
    }


    @PUT
    public String updatePerson(
            @QueryParam("id") String person_id,
            @QueryParam("personName") String name,
            @QueryParam("personPatronymic") String patronymic,
            @QueryParam("personSurname") String surname,
            @QueryParam("personAge") String age,
            @QueryParam("personGender") String gender)
            throws EmptyFieldException, CastIntException, MarkFieldException,
            RowNotExistsException {

        String status;
        List<String> updateArgs = new ArrayList<>();

        if (person_id != null && !person_id.trim().isEmpty()) {
            if ((name != null && !name.trim().isEmpty()) ||
                    (patronymic != null && !patronymic.trim().isEmpty()) ||
                    (surname != null && !surname.trim().isEmpty()) ||
                    (age != null && !age.trim().isEmpty()) ||
                    (gender != null && !gender.trim().isEmpty())) {

                try {
                    Integer.parseInt(person_id.trim());
                    if (name != null && !name.trim().isEmpty()) updateArgs.add("name = '" + name + "'");
                    if (patronymic != null && !patronymic.trim().isEmpty()) updateArgs.add("patronymic = '" + patronymic + "'");
                    if (surname != null && !surname.trim().isEmpty()) updateArgs.add("surname = '" + surname + "'");
                    try {
                        if (age != null && !age.trim().isEmpty()) {
                            Integer.parseInt(age.trim());
                            updateArgs.add("age = '" + age + "'");
                        }
                    } catch (NumberFormatException e) {
                        throw new CastIntException("Произошла ошибка при попытке преобразования " +
                                "'age' преобразуется в целое число. ");
                    }
                } catch (NumberFormatException e) {
                    throw new CastIntException("Произошла ошибка при попытке преобразовать 'id' в целое число.");
                }
            } else {
                throw EmptyFieldException.DEFAULT_INSTANCE;
            }
        } else {
            throw new EmptyFieldException("id не может быть пустым!");
        }

        if (gender != null && !gender.trim().isEmpty()) {
            if (gender.equals("мужской") ||
                    gender.equals("женский")) {
                updateArgs.add("gender = '" + gender + "'");
            } else {
                throw MarkFieldException.DEFAULT_INSTANCE;
            }
        }

        status = new PostgreSQLDAO().updatePerson(Integer.parseInt(person_id), updateArgs);
        if (status.equals("0")) {
            throw RowNotExistsException.DEFAULT_INSTANCE;
        }

        return status;
    }
}