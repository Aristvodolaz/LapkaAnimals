package com.example.newanimals.network.request;


import java.util.List;

public class GeocoderAddressBody {
    private List<String> data;

    public GeocoderAddressBody(List<String> data) {
        this.data = data;
    }
}
