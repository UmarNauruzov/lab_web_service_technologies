package com.soap.error;

public class AuthFault {
    private static final String DEFAULT_MESSAGE = "Неверный логин или пароль";
    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static AuthFault defaultInstance() {
        AuthFault fault = new AuthFault();
        fault.message = DEFAULT_MESSAGE;
        return fault;
    }

}
