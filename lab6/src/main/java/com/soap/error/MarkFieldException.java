package com.soap.error;

public class MarkFieldException extends Exception {
    private static final long serialVersionUID = -6647544772732631047L;

    public static MarkFieldException DEFAULT_INSTANCE = new MarkFieldException("Поле 'gender'" +
            " Поле 'gender' должно иметь одно из следующих значений: 'мужской', " +
            "'женский'");

    public MarkFieldException(String message) {
        super(message);
    }
}
