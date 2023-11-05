package com.soap.error;

public class FieldValueFault {
    private static final String DEFAULT_MESSAGE = "Поле не содержит ни одного из обязательных значений.";
    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static FieldValueFault defaultInstance() {
        FieldValueFault fault = new FieldValueFault();
        fault.message = DEFAULT_MESSAGE;
        return fault;
    }
}