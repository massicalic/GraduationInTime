package com.example.graduationintime;

import java.io.Serializable;
import java.util.ArrayList;

class Curriculum implements Serializable {

    private String place;
    private String citizenship;
    private String cf;
    private String address;
    private String licens;
    private Boolean withCar;
    private String cellphone;
    private String email;
    private String links;
    private String goal;
    private ArrayList<Integer> skills = new ArrayList<>();
    private ArrayList<String> experiences = new ArrayList<>();
    private ArrayList<String> education = new ArrayList<>();
    private ArrayList<Integer> tipeEducation = new ArrayList<>();
    private String languagesNative;
    private ArrayList<String> languagesKnow = new ArrayList<>();
    private ArrayList<ArrayList<String>> knowledge = new ArrayList<>();
    private ArrayList<String> it = new ArrayList<>();
    private String province;
    private ArrayList<Integer> move = new ArrayList<>();

    public Curriculum() { }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicens() {
        return licens;
    }

    public void setLicens(String licens) {
        this.licens = licens;
    }

    public Boolean isWithCar() {
        return withCar;
    }

    public void setWithCar(Boolean withCar) {
        this.withCar = withCar;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public ArrayList<Integer> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Integer> skills) {
        this.skills = skills;
    }

    public ArrayList<String> getExperiences() {
        return experiences;
    }

    public void setExperiences(ArrayList<String> experiences) {
        this.experiences = experiences;
    }

    public ArrayList<String> getEducation() {
        return education;
    }

    public void setEducation(ArrayList<String> education) {
        this.education = education;
    }

    public ArrayList<Integer> getTipeEducation() {
        return tipeEducation;
    }

    public void setTipeEducation(ArrayList<Integer> tipeEducation) {
        this.tipeEducation = tipeEducation;
    }

    public String getLanguagesNative() {
        return languagesNative;
    }

    public void setLanguagesNative(String languagesNative) {
        this.languagesNative = languagesNative;
    }

    public ArrayList<String> getLanguagesKnow() {
        return languagesKnow;
    }

    public void setLanguagesKnow(ArrayList<String> languagesKnow) {
        this.languagesKnow = languagesKnow;
    }

    public ArrayList<ArrayList<String>> getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(ArrayList<ArrayList<String>> knowledge) {
        this.knowledge = knowledge;
    }

    public ArrayList<String> getIt() {
        return it;
    }

    public void setIt(ArrayList<String> it) {
        this.it = it;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public ArrayList<Integer> getMove() {
        return move;
    }

    public void setMove(ArrayList<Integer> move) {
        this.move = move;
    }
}
