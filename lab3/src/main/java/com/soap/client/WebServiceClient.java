package com.soap.client;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebServiceClient {
    public static void main(String[] args) throws MalformedURLException {
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

    private static void updatePersonMethod(PersonServiceCRUD personService) {

        String status = "Bad";

        // Консольный ввод аргументов
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите personID (число): ");
        String PersonIDString = scanner.nextLine();


        System.out.println("Какие поля вы хотите обновить для этой строки? \n" +
                "Выберите поля \"name\", \"patronymic\", \"surname\", \"age\", \"gender\" и введите их ниже \n" +
                "разделяется запятой без пробелов");
        String updateRows = scanner.nextLine();

        // Преобразуем полученную строку в список аргументов
        String[] updateRowsList = updateRows.split(",", -1);

        Map<String, String> updateRowsMap = new HashMap<>();
        updateRowsMap.put("name", "");
        updateRowsMap.put("patronymic", "");
        updateRowsMap.put("surname", "");
        updateRowsMap.put("age", "");
        updateRowsMap.put("gender", "");


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

            System.out.println("Вы вводите новые значения для строки " + PersonIDString + ":\n" + updateRowsMap);
            System.out.println("Вы действительно хотите изменить эти поля для строки " + PersonIDString + "? (y -> yes, other -> no)");
            String agree = scanner.nextLine();
            if (agree.equals("y")) {
                try {
                    status = personService.getPersonWebServicePort().updatePerson(
                            PersonIDString,
                            updateRowsMap.get("name"),
                            updateRowsMap.get("patronymic"),
                            updateRowsMap.get("surname"),
                            updateRowsMap.get("age"),
                            updateRowsMap.get("gender"));
                } catch (ForIntException | EmptyFieldException | FieldValueException | IdNotExistsException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Вы просто отменяете свой запрос. Попробуйте другой запрос или выйдите.");
            }
        System.out.println("Status: " + status);
    }

    private static void deletePersonMethod(PersonServiceCRUD personService) {
        String status = "0";

        // Консольный ввод аргументов
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите идентификатор пользователя (целое число): ");
        String personIDString = scanner.nextLine();

        try {
            status = personService.getPersonWebServicePort().deletePerson(personIDString);
        } catch (NumberFormatException | ForIntException | IdNotExistsException ex) {
            Logger.getLogger(WebServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Status: " + status);
    }



    private static void readPersonMethod(PersonServiceCRUD personService) {
        List<Person> persons = personService.getPersonWebServicePort().getPersons();
        for (Person person : persons) {
            System.out.println("id: " + person.getPersonId() + " name: " + person.getName() + ", surname: " + person.getSurname() + ", age: " +
                    person.getAge());
        }
    }

    private static void createPersonMethod(PersonServiceCRUD personService) {

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
            try {
                status = personService.getPersonWebServicePort().createPerson(name, patronymic, surname, ageString, gender);
            } catch (ForIntException | EmptyFieldException | FieldValueException e) {
                e.printStackTrace();
            }
            System.out.println("Status: " + status);
        }
        else {
            System.out.println("Ваш запрос неверен!");
        }
    }
}