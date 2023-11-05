package com.soap.error;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "com.soap.error.ForIntException")
public class ForIntException extends Exception {
    private static final long serialVersionUID = -6647544772732631047L;
    private final ForIntFault fault;

    public ForIntException(String message, ForIntFault fault) {
        super(message);
        this.fault = fault;
    }

    public ForIntException(String message, ForIntFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }

    public ForIntFault getFaultInfo() {
        return fault;
    }

}