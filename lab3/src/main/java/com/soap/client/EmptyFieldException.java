
package com.soap.client;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.0.2
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "EmptyFieldException", targetNamespace = "http://soap.com/")
public class EmptyFieldException
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private EmptyFieldFault faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public EmptyFieldException(String message, EmptyFieldFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public EmptyFieldException(String message, EmptyFieldFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.soap.client.EmptyFieldFault
     */
    public EmptyFieldFault getFaultInfo() {
        return faultInfo;
    }

}
