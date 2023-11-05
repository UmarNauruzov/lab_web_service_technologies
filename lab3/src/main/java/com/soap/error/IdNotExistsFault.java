package com.soap.error;

public class IdNotExistsFault  {
    private static final String DEFAULT_MESSAGE = "Cтроки с этим Id не существует.";
    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static IdNotExistsFault  defaultInstance() {
        IdNotExistsFault  fault = new IdNotExistsFault ();
        fault.message = DEFAULT_MESSAGE;
        return fault;
    }
}