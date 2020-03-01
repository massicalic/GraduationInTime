package com.example.graduationintime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class User implements Serializable {

    private String name;
    private String surname;
    private String email;
    private String psw;
    private int matriculation;
    private GregorianCalendar birthdate;
    private int day;
    private int month;
    private int year;
    private int yearEnroll;
    private boolean moved;
    private int studyTime;
    private String imageUrl;
    private ArrayList<Exam> exams = new ArrayList<>();
    private String thesis;
    private String thesisInfo;
    private Curriculum curriculum = new Curriculum();

    public User() {
    }

    public User(String name, String surname, String email, String psw, GregorianCalendar birthdate, int yearEnroll, boolean moved) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.psw = psw;
        this.birthdate = birthdate;
        this.yearEnroll = yearEnroll;
        this.moved = moved;
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

    public int getMatriculation() {
        return matriculation;
    }

    public void setMatriculation(int matriculation) {
        this.matriculation = matriculation;
    }

    public GregorianCalendar getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(GregorianCalendar birthdate) {
        this.birthdate = birthdate;
    }

    public int getYearEnroll() {
        return yearEnroll;
    }

    public void setYearEnroll(int yearEnroll) {
        this.yearEnroll = yearEnroll;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public int getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(int studyTime) {
        this.studyTime = studyTime;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<Exam> getExams() {
        return exams;
    }

    public void setExams(ArrayList<Exam> exams) {
        this.exams = exams;
    }

    public String getThesis() {
        return thesis;
    }

    public void setThesis(String thesis) {
        this.thesis = thesis;
    }

    public String getThesisInfo() {
        return thesisInfo;
    }

    public void setThesisInfo(String thesisInfo) {
        this.thesisInfo = thesisInfo;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }
}
