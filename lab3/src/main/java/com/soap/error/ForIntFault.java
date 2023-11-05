package com.soap.error;

public class ForIntFault {
    private static final String DEFAULT_MESSAGE = "Некоторые параметры должны быть преобразованы в int!!";
    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ForIntFault defaultInstance() {
        ForIntFault fault = new ForIntFault();
        fault.message = DEFAULT_MESSAGE;
        return fault;
    }
}