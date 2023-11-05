package com.soap.error;

public class EmptyFieldFault {
    private static final String DEFAULT_MESSAGE = "Этот метод не может содержать параметров с пустыми строка " +
            "или только с пробелами!";
    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static EmptyFieldFault defaultInstance() {
        EmptyFieldFault fault = new EmptyFieldFault();
        fault.message = DEFAULT_MESSAGE;
        return fault;
    }

}
