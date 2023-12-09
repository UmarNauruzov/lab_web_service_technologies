package com.soap.error;

public class AuthException extends Exception {
    private static final long serialVersionUID = -6647544772732631047L;

    public static AuthException DEFAULT_INSTANCE = new AuthException("Значение логина или пароля неверны.");

    public AuthException(String message) {
        super(message);
    }
}
