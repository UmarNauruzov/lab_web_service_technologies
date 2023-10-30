package com.soap.client;

import com.soap.client.Person;
import com.soap.client.PersonService;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class WebServiceClient {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:8081/PersonService?wsdl");
        PersonService personService = new PersonService(url);

        List<Person> persons = personService.getPersonWebServicePort().getPersons();
        for (Person person : persons) {
            System.out.println("name: " + person.getName() + ", surname: " + person.getSurname() + ", age: " +
                    person.getAge());
        }
    }

    private static List<String> getArgs() {
        List<String> given_args = new ArrayList<String>();

        // Консольный ввод аргументов
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input arguments for search (one line = one argument, input 'exit' for exit): ");
        String given_arg;
        do {
            given_arg = scanner.nextLine();
            // проверим строку на наличие аргумента: если строка не является пустой и не состоит из пробелов, то
            // добавляем аргумент в массив
            if (given_arg != null && !given_arg.trim().isEmpty()) {
                given_args.add(given_arg);
            }
        } while (!Objects.equals(given_arg, "exit"));

        return given_args;
    }
}