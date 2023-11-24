package com.soap.error;

public class RowNotExistsException extends Exception {
    private static final long serialVersionUID = -6647544772732631047L;

    public static RowNotExistsException DEFAULT_INSTANCE = new RowNotExistsException("Строки с этим идентификатором не существует.");

    public RowNotExistsException(String message) {
        super(message);
    }
}