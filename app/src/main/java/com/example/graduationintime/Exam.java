package com.example.graduationintime;

import java.util.GregorianCalendar;

public class Exam {

    private String name;
    private GregorianCalendar date_time;
    private String place;
    private String prof;
    private String info;
    private boolean notification = false;

    public Exam() {
    }

    public Exam(String name, GregorianCalendar date_time, String place, String prof, String info) {
        this.name = name;
        this.date_time = date_time;
        this.place = place;
        this.prof = prof;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GregorianCalendar getDate_time() {
        return date_time;
    }

    public void setDate_time(GregorianCalendar date_time) {
        this.date_time = date_time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }
}
