package com.example.newanimals.db;

public class PersonData {
    String type;
    String name_type;
    String name;
    String surname;
    String city;

    String phone;
    String date_birth;
    String login;

    String img;


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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName_type() {
        return name_type;
    }

    public void setName_type(String name_type) {
        this.name_type = name_type;
    }

    public String getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(String date_birth) {
        this.date_birth = date_birth;
    }

    public PersonData() {
    }

    public PersonData(String type, String name_type, String name, String surname, String city, String phone, String date_birth, String login, String img) {
        this.type = type;
        this.name_type = name_type;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.phone = phone;
        this.date_birth = date_birth;
        this.login = login;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
