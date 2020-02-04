package com.example.graduationintime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class User implements Serializable {

    private String name;
    private String surname;
    private String email;
    private String psw;
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
        exams.add(new Exam("Algebra E Geometria", 9));
        exams.add(new Exam("Algoritmi E Strutture Dati", 9));
        exams.add(new Exam("Analisi Matematica", 9));
        exams.add(new Exam("Architettura Degli Elaboratori", 9));
        exams.add(new Exam("Programmazione", 12));
        exams.add(new Exam("Basi Di Dati", 9));
        exams.add(new Exam("Informazione, Trasmissione E Codici A Protezione D'errore", 6));
        exams.add(new Exam("Laboratorio A", 3));
        exams.add(new Exam("Laboratorio B", 3));
        exams.add(new Exam("Logica", 6));
        exams.add(new Exam("Progettazione Del Software", 8));
        exams.add(new Exam("Programmazione Concorrente E Distribuita", 8));
        exams.add(new Exam("Sistemi Operativi", 8));
        exams.add(new Exam("Automi E Linguaggi", 6));
        exams.add(new Exam("Fondamenti Di Sicurezza", 6));
        exams.add(new Exam("Reti Di Telecomunicazione", 9));
    }

    public User(String name, String surname, String email, String psw, GregorianCalendar birthdate, int yearEnroll, boolean moved) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.psw = psw;
        this.birthdate = birthdate;
        this.yearEnroll = yearEnroll;
        this.moved = moved;

        exams.add(new Exam("Algebra E Geometria", 9));
        exams.add(new Exam("Algoritmi E Strutture Dati", 9));
        exams.add(new Exam("Analisi Matematica", 9));
        exams.add(new Exam("Architettura Degli Elaboratori", 9));
        exams.add(new Exam("Programmazione", 12));
        exams.add(new Exam("Basi Di Dati", 9));
        exams.add(new Exam("Informazione, Trasmissione E Codici A Protezione D'errore", 6));
        exams.add(new Exam("Laboratorio Interdisciplinare A", 3));
        exams.add(new Exam("Laboratorio Interdisciplinare B", 3));
        exams.add(new Exam("Logica", 6));
        exams.add(new Exam("Progettazione Del Software", 8));
        exams.add(new Exam("Programmazione Concorrente E Distribuita", 8));
        exams.add(new Exam("Sistemi Operativi", 8));
        exams.add(new Exam("Automi E Linguaggi", 6));
        exams.add(new Exam("Fondamenti Di Sicurezza", 6));
        exams.add(new Exam("Reti Di Telecomunicazione", 9));
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
