package com.soap;

import com.soap.error.*;

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
                               @WebParam(name = "personAge") String age,
                               @WebParam(name = "personGender") String gender) throws EmptyFieldException, ForIntException, FieldValueException {

        String status;

        if (name != null && !name.trim().isEmpty() &&
                patronymic != null && !patronymic.trim().isEmpty() &&
                surname != null && !surname.trim().isEmpty() &&
                age != null && !age.trim().isEmpty() &&
                gender != null && !gender.trim().isEmpty()) {

            try {
                int ageInt = Integer.parseInt(age.trim());;

                if (gender.equals("мужской") ||
                        gender.equals("женский")) {

                    PostgreSQLDAO dao = new PostgreSQLDAO();
                    status = dao.createPerson(name, patronymic, surname, ageInt, gender);

                } else {
                    FieldValueFault fault = FieldValueFault.defaultInstance();
                    throw new FieldValueException("В классе произошла ошибка: " + PersonWebService.class.getName() +
                            ", метод - createPerson(). \n Поле 'gender' не имеет ни одного из " +
                            "значений: 'мужской' или 'женский'.",
                            fault);
                }

            } catch (NumberFormatException ex) {
                ForIntFault fault = ForIntFault.defaultInstance();
                throw new ForIntException("В классе произошла ошибка: " + PersonWebService.class.getName() +
                        ", метод - createPerson(). \n Мы получаем 'NumberFormatException': " + ex +
                        ", при попытке преобразовать возраст человека в целые числа.", fault);
            }


        } else {
            EmptyFieldFault fault = EmptyFieldFault.defaultInstance();
            throw new EmptyFieldException("В классе произошла ошибка " + PersonWebService.class.getName() +
                    ", метод createPerson().", fault);
        }

        return status;
    }

    @WebMethod(operationName = "deletePerson")
    public String deletePerson(@WebParam(name = "person_id") String person_id) throws ForIntException, IdNotExistsException {
        String status;
        try {
            int personIdInt = Integer.parseInt(person_id.trim());
            PostgreSQLDAO dao = new PostgreSQLDAO();
            status = dao.deletePerson(personIdInt);
            System.out.println(status);
            if (status.equals("0")) {
                IdNotExistsFault fault = IdNotExistsFault.defaultInstance();
                throw new IdNotExistsException("В классе произошла ошибка: " + PersonWebService.class.getName() +
                        ", метод - deletePerson(). \n Мы получаем 'status = 0' после удаления строки в таблице в БД " + person_id, fault);
            }

        } catch (NumberFormatException ex) {
            ForIntFault fault = ForIntFault.defaultInstance();
            throw new ForIntException("В классе произошла ошибка: " + PersonWebService.class.getName() +
                    ", метод - deletePerson(). \n Мы получаем 'NumberFormatException': " + ex +
                    ", при попытке преобразовать строки в int.", fault);
        }

        return status;
    }


    @WebMethod(operationName = "updatePerson")
    public String updatePerson(@WebParam(name = "person_id") String person_id,
                               @WebParam(name = "personName") String name,
                               @WebParam(name = "personPatronymic") String patronymic,
                               @WebParam(name = "personSurname") String surname,
                               @WebParam(name = "personAge") String age,
                               @WebParam(name = "personGender") String gender) throws EmptyFieldException,
            ForIntException,
            IdNotExistsException,
            FieldValueException {

        String status;

        try {
            int personIdInt = Integer.parseInt(person_id.trim());

            Integer.parseInt(age.trim());

            List<String> updateArgs = new ArrayList<>();

            if (name != null && !name.trim().isEmpty()) updateArgs.add("name = '" + name + "'");
            if (surname != null && !surname.trim().isEmpty()) updateArgs.add("surname = '" + surname + "'");

            if (!age.trim().isEmpty()) {
                try {
                    Integer.parseInt(age.trim());
                    updateArgs.add("age = '" + age + "'");
                } catch (NumberFormatException ex) {
                    ForIntFault fault = ForIntFault.defaultInstance();
                    throw new ForIntException("В классе произошла ошибка: " + PersonWebService.class.getName() +
                            ", метод - updatePerson(). \n Мы получаем 'NumberFormatException': " + ex +
                            ", при попытке преобразовать \"age\" в \"int\".", fault);
                }
            }


            if (gender != null && !gender.trim().isEmpty()) {
                if (gender.equals("мужской") ||
                        gender.equals("женский")) {
                    updateArgs.add("пол = '" + gender + "'");
                } else {
                    FieldValueFault fault = FieldValueFault.defaultInstance();
                    throw new FieldValueException("В классе произошла ошибка:" + PersonWebService.class.getName() +
                            ",  метод - updatePerson(). \n Поле \"пол\" не содержит ни одного из " +
                            "значение: 'мужчина' или 'женщина'.",
                            fault);
                }
            }

            int i = 0;
            for (String param : updateArgs) {
                if (param != null && !param.trim().isEmpty()) {
                    i++;
                }
            }
            if (i > 0) {

                PostgreSQLDAO dao = new PostgreSQLDAO();
                status = dao.updatePerson(personIdInt, updateArgs);

            } else {
                EmptyFieldFault fault = EmptyFieldFault.defaultInstance();
                fault.setMessage("Все необходимые параметры пусты. Пожалуйста, введите хотя бы один из них.");
                throw new EmptyFieldException("В классе произошла ошибка " + PersonWebService.class.getName() +
                        ", метод updatePerson().", fault);
            }

            if (status.equals("0")) {
                IdNotExistsFault fault = IdNotExistsFault.defaultInstance();
                throw new IdNotExistsException("В классе произошла ошибка " + PersonWebService.class.getName() +
                        ", метод - updatePerson(). \n Мы получаем 'status = 0' после удаления строки в таблице в БД " + person_id, fault);
            }

        } catch (NumberFormatException ex) {
            ForIntFault fault = ForIntFault.defaultInstance();
            throw new ForIntException("В классе произошла ошибка " + PersonWebService.class.getName() +
                    ", метод - updatePerson(). \n Мы получаем 'NumberFormatException': " + ex +
                    ", при попытке преобразовать строки в int.", fault);
        }

        return status;
    }
}
