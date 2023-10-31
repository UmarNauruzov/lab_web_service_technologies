package com.soap;

public class Person {
    private String name;
    private String patronymic;
    private String surname;
    private int age;
    private String gender;

    public Person(String name, String patronymic, String surname, int age, String gender) {
        this.name = name;
        this.patronymic = patronymic;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
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
    public void setName(String name) {
        this.name = name;
    }
    public void setPatronymic(String patronymic) {
        this.surname = patronymic;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setGender(String gender) {
        this.surname = gender;
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", patronymic=" + patronymic +", surname=" + surname +
                ", age=" + age + ", gender=" + gender +'}';
    }
}