package com.example.newanimals.db;

public class TypeAdressData {
    String type;
    String address;

    public TypeAdressData(String type, String address) {
        this.type = type;
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
