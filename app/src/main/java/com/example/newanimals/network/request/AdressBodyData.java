package com.example.newanimals.network.request;

import com.google.gson.annotations.SerializedName;

public class AdressBodyData {
    @SerializedName("lat") Float lat;
    @SerializedName("lon") Float lon;

    public AdressBodyData(Float lat, Float lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
