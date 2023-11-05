package com.soap.error;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "com.soap.error.IdNotExistsException")
public class IdNotExistsException extends Exception {
    private static final long serialVersionUID = -6647544772732631047L;
    private final IdNotExistsFault fault;

    public IdNotExistsException(String message, IdNotExistsFault fault) {
        super(message);
        this.fault = fault;
    }

    public IdNotExistsException(String message, IdNotExistsFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }

    public IdNotExistsFault getFaultInfo() {
        return fault;
    }
}