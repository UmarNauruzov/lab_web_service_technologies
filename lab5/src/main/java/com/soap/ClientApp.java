package com.soap;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class ClientApp {
    private static final String URL = "http://localhost:8080/restCRUD/persons";
    public static void main(String[] args) {
        Client client = Client.create();

            // Выбор метода CRUD
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите метод CRUD (введите CREATE, READ, SEARCH, UPDATE или DELETE) или введите \"exit\" для выхода:");
        String choosenMethod;
        do {
            choosenMethod = scanner.nextLine();
            // проверяем строку на наличие аргумента: если строка не является пустой и не состоит из пробелов, то
            // проверяем на наличие одной из возможных операций CRUD
            if (choosenMethod != null && !choosenMethod.trim().isEmpty()) {
                switch (choosenMethod) {
                     case ("CREATE"):
                         createPerson(client);
                         System.out.println("Вот и все! Вы можете выбрать другой метод CRUD или ввести \"exit\" для выхода\"");
                         break;
                     case ("READ"):
                         readPerson(client);
                         System.out.println("Вот и все! Вы можете выбрать другой метод CRUD или ввести \"exit\" для выхода\"");
                         break;
                     case ("SEARCH"):
                        searchPerson(client);
                        System.out.println("Вот и все! Вы можете выбрать другой метод CRUD или ввести \"exit\" для выхода\"");
                        break;
                     case ("UPDATE"):
                         updatePerson(client);
                         System.out.println("Вот и все! Вы можете выбрать другой метод CRUD или ввести \"exit\" для выхода\"");
                         break;
                     case ("DELETE"):
                         deletePerson(client);
                         System.out.println("Вот и все! Вы можете выбрать другой метод CRUD или ввести \"exit\" для выхода\"");
                         break;
                     case ("exit"):
                         System.out.println("Bye-Bye!");
                         break;
                     default:
                         System.out.println("Вы можете ввести просто CREATE, READ, SEARCH, UPDATE или DELETE!");
                         System.out.println("Повторите попытку или используйте \"exit\" для выхода.");
                         break;
                }
            }
        } while (!Objects.equals(choosenMethod, "exit"));
        scanner.close();
    }

    private static void updatePerson(Client client) {
        // Консольный ввод аргументов
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите personID (целое число): ");
        String PersonIDString = scanner.nextLine();
        // Проверяем personId на число
        int personId = -1;
        if (PersonIDString != null && !PersonIDString.trim().isEmpty()) {
            try {
                personId = Integer.parseInt(PersonIDString.trim());
            } catch (NumberFormatException ex) {
                Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
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
                            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
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
                    String name = updateRowsMap.get("name");
                    String patronymic = updateRowsMap.get("patronymic");
                    String surname = updateRowsMap.get("surname");
                    String age = updateRowsMap.get("age");
                    String gender = updateRowsMap.get("gender");

                    WebResource webResource = client.resource(URL);
                    webResource = webResource.queryParam("id",
                            PersonIDString).queryParam("PersonName", name).queryParam("personPatronymic",
                            patronymic).queryParam("personSurname",
                            surname).queryParam("personAge", age).queryParam("studentGender", gender);
                    ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).put(ClientResponse.class);
                    if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
                        throw new IllegalStateException("Запрос не выполнен");
                    }
                    System.out.println(response.getStatus());
                } else {
                    System.out.println("Вы просто отменяете свой запрос. Попробуйте другой запрос или выйдите.");
                }
            } else {
                System.out.println("Все аргументы пусты. Обновление строки не может быть завершено.");
            }
        }
    }


    private static void deletePerson(Client client) {
        // Консольный ввод аргументов
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите идентификатор пользователя (целое число): ");
        String personId = scanner.nextLine();

        try {
            Integer.parseInt(personId.trim());
            WebResource webResource = client.resource(URL);
            webResource = webResource.queryParam("id", personId);
            ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
            if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
                throw new IllegalStateException("Запрос не выполнен");
            }
            System.out.println(response.getStatus());
        } catch (NumberFormatException ex) {
            System.out.println("Неверное значение personId! Введите только одно целое число..");
        }
    }


    private static void readPerson(Client client) {
        WebResource webResource = client.resource(URL);
        String query = "";
        webResource = webResource.queryParam("query", query);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Запрос выполнен");
        }
        GenericType<List<Person>> type = new GenericType<List<Person>>() {};

        for (Person person : response.getEntity(type)) {
            System.out.println(person);
        }
    }

    private static void searchPerson(Client client) {
        List<String> searchThis = new ArrayList<>();
        // Консольный ввод аргументов
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ввод аргументов для поиска (одна строка = один аргумент, введите 'exit' для выхода, " +
                "после чего выводится список): ");
        String given_arg;
        do {
            given_arg = scanner.nextLine();
            // проверим строку на наличие аргумента
            if (given_arg != null && !given_arg.trim().isEmpty()) {
                searchThis.add(given_arg);
            }
        } while (!Objects.equals(given_arg, "exit"));

        WebResource webResource = client.resource(URL);

        MultivaluedMap<String, String> reqParams = new MultivaluedMapImpl();
        reqParams.put("searchParam", searchThis);
        webResource = webResource.queryParams(reqParams);

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Запрос не выполнен");
        }
        GenericType<List<Person>> type = new GenericType<List<Person>>() {};

        for (Person person : response.getEntity(type)) {
            System.out.println(person);
        }
    }

    private static void createPerson(Client client) {
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


        // проверим ввод на наличие значений: строка не является пустой и не состоит из пробелов
        if ((name != null && !name.trim().isEmpty())  &&
                (patronymic != null && !patronymic.trim().isEmpty())  &&
                (surname != null && !surname.trim().isEmpty()) &&
                (ageString != null && !ageString.trim().isEmpty()) &&
                (gender != null && !gender.trim().isEmpty())) {
            try {
                Integer.parseInt(ageString.trim());

                WebResource webResource = client.resource(URL);

                webResource = webResource.queryParam("personName", name).queryParam("personPatronymic",
                        patronymic).queryParam("personSurname", surname).queryParam("personAge", ageString).queryParam("personGender", gender);

                ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class);
                if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
                    throw new IllegalStateException("Запрос не выполнен");
                }
                System.out.println(response.getStatus());

            } catch (NumberFormatException ex) {
                System.out.println("Неверный возраст!");
            }
        }
        else {
            System.out.println("Ваш запрос неверен!");
        }
    }
}