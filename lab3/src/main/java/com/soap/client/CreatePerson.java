
package com.soap.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createPerson complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createPerson"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="personName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="personPatronymic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="personSurname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="personAge" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="personGender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createPerson", propOrder = {
    "personName",
    "personPatronymic",
    "personSurname",
    "personAge",
    "personGender"
})
public class CreatePerson {

    protected String personName;
    protected String personPatronymic;
    protected String personSurname;
    protected String personAge;
    protected String personGender;

    /**
     * Gets the value of the personName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * Sets the value of the personName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonName(String value) {
        this.personName = value;
    }

    /**
     * Gets the value of the personPatronymic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonPatronymic() {
        return personPatronymic;
    }

    /**
     * Sets the value of the personPatronymic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonPatronymic(String value) {
        this.personPatronymic = value;
    }

    /**
     * Gets the value of the personSurname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonSurname() {
        return personSurname;
    }

    /**
     * Sets the value of the personSurname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonSurname(String value) {
        this.personSurname = value;
    }

    /**
     * Gets the value of the personAge property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonAge() {
        return personAge;
    }

    /**
     * Sets the value of the personAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonAge(String value) {
        this.personAge = value;
    }

    /**
     * Gets the value of the personGender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonGender() {
        return personGender;
    }

    /**
     * Sets the value of the personGender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonGender(String value) {
        this.personGender = value;
    }

}
