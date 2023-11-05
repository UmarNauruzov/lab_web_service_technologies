
package com.soap.client;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.0.2
 * Generated source version: 2.2
 * 
 */
@WebService(name = "PersonWebService", targetNamespace = "http://soap.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface PersonWebService {


    /**
     * 
     * @return
     *     returns java.util.List<com.soap.client.Person>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPersons", targetNamespace = "http://soap.com/", className = "com.soap.client.GetPersons")
    @ResponseWrapper(localName = "getPersonsResponse", targetNamespace = "http://soap.com/", className = "com.soap.client.GetPersonsResponse")
    @Action(input = "http://soap.com/PersonWebService/getPersonsRequest", output = "http://soap.com/PersonWebService/getPersonsResponse")
    public List<Person> getPersons();

    /**
     * 
     * @param personName
     * @param personAge
     * @param personSurname
     * @param personGender
     * @param personPatronymic
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "createPerson", targetNamespace = "http://soap.com/", className = "com.soap.client.CreatePerson")
    @ResponseWrapper(localName = "createPersonResponse", targetNamespace = "http://soap.com/", className = "com.soap.client.CreatePersonResponse")
    @Action(input = "http://soap.com/PersonWebService/createPersonRequest", output = "http://soap.com/PersonWebService/createPersonResponse")
    public String createPerson(
        @WebParam(name = "personName", targetNamespace = "")
        String personName,
        @WebParam(name = "personPatronymic", targetNamespace = "")
        String personPatronymic,
        @WebParam(name = "personSurname", targetNamespace = "")
        String personSurname,
        @WebParam(name = "personAge", targetNamespace = "")
        int personAge,
        @WebParam(name = "personGender", targetNamespace = "")
        String personGender);

    /**
     * 
     * @param personId
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deletePerson", targetNamespace = "http://soap.com/", className = "com.soap.client.DeletePerson")
    @ResponseWrapper(localName = "deletePersonResponse", targetNamespace = "http://soap.com/", className = "com.soap.client.DeletePersonResponse")
    @Action(input = "http://soap.com/PersonWebService/deletePersonRequest", output = "http://soap.com/PersonWebService/deletePersonResponse")
    public String deletePerson(
        @WebParam(name = "person_id", targetNamespace = "")
        int personId);

    /**
     * 
     * @param personName
     * @param personAge
     * @param personSurname
     * @param personGender
     * @param personId
     * @param personPatronymic
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "updatePerson", targetNamespace = "http://soap.com/", className = "com.soap.client.UpdatePerson")
    @ResponseWrapper(localName = "updatePersonResponse", targetNamespace = "http://soap.com/", className = "com.soap.client.UpdatePersonResponse")
    @Action(input = "http://soap.com/PersonWebService/updatePersonRequest", output = "http://soap.com/PersonWebService/updatePersonResponse")
    public String updatePerson(
        @WebParam(name = "person_id", targetNamespace = "")
        int personId,
        @WebParam(name = "personName", targetNamespace = "")
        String personName,
        @WebParam(name = "personPatronymic", targetNamespace = "")
        String personPatronymic,
        @WebParam(name = "personSurname", targetNamespace = "")
        String personSurname,
        @WebParam(name = "personAge", targetNamespace = "")
        int personAge,
        @WebParam(name = "personGender", targetNamespace = "")
        String personGender);

}