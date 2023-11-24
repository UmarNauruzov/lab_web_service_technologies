package com.soap.error;


public class EmptyFieldException extends Exception {
    private static final long serialVersionUID = -6647544772732631047L;

    public static EmptyFieldException DEFAULT_INSTANCE = new EmptyFieldException("Некоторые поля не могут быть нулевыми или пустыми");

    public EmptyFieldException(String message) {
        super(message);
    }
}
