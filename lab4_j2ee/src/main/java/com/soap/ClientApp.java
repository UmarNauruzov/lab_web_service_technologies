package com.soap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import java.util.List;
import javax.ws.rs.core.MediaType;
public class ClientApp {
    private static final String URL =
            "http://localhost:8080/lab4/rest/persons";
    public static void main(String[] args) {
        Client client = Client.create();
        printList(getAllPersons(client, null));
        System.out.println();
        printList(getAllPersons(client, "Васильевич"));
    }
    private static List<Person> getAllPersons(Client client, String patronymic)
    {
        WebResource webResource = client.resource(URL);
        if (patronymic != null) {
            webResource = webResource.queryParam("searchParam", patronymic);
        }
        ClientResponse response =
                webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        if (response.getStatus() !=
                ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        GenericType<List<Person>> type = new GenericType<List<Person>>()
        {};
        return response.getEntity(type);
    }
    private static void printList(List<Person> persons) {
        for (Person person : persons) {
            System.out.println(person);
        }
    }
}