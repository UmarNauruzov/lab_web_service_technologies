package com.soap.error;

public class CastIntException extends Exception {
    private static final long serialVersionUID = -6647544772732631047L;

    public static CastIntException DEFAULT_INSTANCE = new CastIntException("Значения некоторых полей не могут быть преобразованы в целые числа");

    public CastIntException(String message) {
        super(message);
    }
}
