package com.soap;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {
    private int person_id;
    private String name;
    private String patronymic;
    private String surname;
    private int age;
    private String gender;

    public Person() {
    }
    public Person(int id, String name, String patronymic, String surname, int age, String gender) {
        this.person_id = id;
        this.name = name;
        this.patronymic = patronymic;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
    }

    public int getPerson_id() {
        return person_id;
    }

    public String getName() {
        return name;
    }
    public String getPatronymic() {
        return patronymic;
    }
    public String getSurname() {
        return surname;
    }
    public int getAge() {
        return age;
    }
    public String getGender() {
        return gender;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{"+"id= " + person_id + ", name= " + name + ", patronymic= " + patronymic +", surname= " + surname +
                ", age= " + age + ", gender= " + gender +'}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Person guest = (Person) obj;
        return person_id == guest.person_id && age == guest.age
                && (name == guest.name || (name != null &&name.equals(guest.getName())))
                && (patronymic == guest.patronymic || (patronymic != null &&patronymic.equals(guest.getName())))
                && (surname == guest.surname || (surname != null && surname .equals(guest.getSurname())))
                && (gender == guest.gender || (gender != null && gender .equals(guest.getGender())));
    }

}