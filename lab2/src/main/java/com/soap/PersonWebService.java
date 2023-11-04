package com.soap;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.WebParam;
import java.util.ArrayList;

@WebService(serviceName = "PersonServiceCRUD")
public class PersonWebService {
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
                                @WebParam(name = "personGender") String gender) {
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.createPerson(name, patronymic, surname, age, gender);
    }

    @WebMethod(operationName = "deletePerson")
    public String deletePerson(@WebParam(name = "person_id") int person_id) {
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.deletePerson(person_id);
    }

    @WebMethod(operationName = "updatePerson")
    public String updatePerson(@WebParam(name = "person_id") int person_id,
                               @WebParam(name = "personName") String name,
                               @WebParam(name = "personPatronymic") String patronymic,
                               @WebParam(name = "personSurname") String surname,
                               @WebParam(name = "personAge") int age,
                               @WebParam(name = "personGender") String gender) {

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