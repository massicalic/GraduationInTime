package com.example.graduationintime;

import java.util.GregorianCalendar;

public class User {

    private String name;
    private String surname;
    private String email;
    private String psw;
    private GregorianCalendar birthdate;

    public User(String name, String surname, String email, String psw, GregorianCalendar birthdate) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.psw = psw;
        this.birthdate = birthdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public GregorianCalendar getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(GregorianCalendar birthdate) {
        this.birthdate = birthdate;
    }
}
