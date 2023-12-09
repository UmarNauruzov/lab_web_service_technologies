package com.soap.error;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "com.soap.error.AuthFault")
public class AuthException extends Exception {

    private static final long serialVersionUID = -6647544772732631047L;
    private final AuthFault fault;

    public AuthException(String message, AuthFault fault) {
        super(message);
        this.fault = fault;
    }

    public AuthException(String message, AuthFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }

    public AuthFault getFaultInfo() {
        return fault;
    }

}