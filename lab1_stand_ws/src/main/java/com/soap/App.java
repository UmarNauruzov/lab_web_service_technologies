package com.soap;

import javax.xml.ws.Endpoint;
public class App {
    public static void main(String[] args) {
        String url = "http://0.0.0.0:8081/PersonService";
        Endpoint.publish(url, new PersonWebService());
    }
}