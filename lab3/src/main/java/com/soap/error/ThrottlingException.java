package com.soap.error;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "com.soap.error.ThrottlingFault")
public class ThrottlingException extends Exception {

    private static final long serialVersionUID = -6647544772732631047L;
    private final ThrottlingFault fault;

    public ThrottlingException(String message, ThrottlingFault fault) {
        super(message);
        this.fault = fault;
    }

    public ThrottlingException(String message, ThrottlingFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }

    public ThrottlingFault getFaultInfo() {
        return fault;
    }
}