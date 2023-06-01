package com.example.newanimals.db;

public class AdsData {
    String type;
    String name_type;
    String name_animals;
    String description;
    String name_people;
    String surname_people;
    String service;
    String address;
    String date_lose;

    public String getDate_lose() {
        return date_lose;
    }

    public void setDate_lose(String date_lose) {
        this.date_lose = date_lose;
    }

    String days;
    String price;
    Float lat;
    Float lon;
    String typeAnimals;
    String imgUrl;

    public String getTypeAnimals() {
        return typeAnimals;
    }

    public void setTypeAnimals(String typeAnimals) {
        this.typeAnimals = typeAnimals;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public AdsData(String type, String name_type, String name_animals, String description, String name_people, String surname_people, String service, String address, String date_lose, String days, String price, Float lat, Float lon, String typeAnimals, String imgUrl) {
        this.type = type;
        this.name_type = name_type;
        this.name_animals = name_animals;
        this.description = description;
        this.name_people = name_people;
        this.surname_people = surname_people;
        this.service = service;
        this.address = address;
        this.date_lose = date_lose;
        this.days = days;
        this.price = price;
        this.lat = lat;
        this.lon = lon;
        this.typeAnimals = typeAnimals;
        this.imgUrl = imgUrl;
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

    public String getName_animals() {
        return name_animals;
    }

    public void setName_animals(String name_animals) {
        this.name_animals = name_animals;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName_people() {
        return name_people;
    }

    public void setName_people(String name_people) {
        this.name_people = name_people;
    }

    public String getSurname_people() {
        return surname_people;
    }

    public void setSurname_people(String surname_people) {
        this.surname_people = surname_people;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }
}
