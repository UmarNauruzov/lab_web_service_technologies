
package com.soap.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updatePerson complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updatePerson">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="person_id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="personName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="personPatronymic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="personSurname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="personAge" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="personGender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updatePerson", propOrder = {
    "personId",
    "personName",
    "personPatronymic",
    "personSurname",
    "personAge",
    "personGender"
})
public class UpdatePerson {

    @XmlElement(name = "person_id")
    protected int personId;
    protected String personName;
    protected String personPatronymic;
    protected String personSurname;
    protected int personAge;
    protected String personGender;

    /**
     * Gets the value of the personId property.
     * 
     */
    public int getPersonId() {
        return personId;
    }

    /**
     * Sets the value of the personId property.
     * 
     */
    public void setPersonId(int value) {
        this.personId = value;
    }

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
     */
    public int getPersonAge() {
        return personAge;
    }

    /**
     * Sets the value of the personAge property.
     * 
     */
    public void setPersonAge(int value) {
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
