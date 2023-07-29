package com.example.newanimals.network.response;

import android.location.Geocoder;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DaDataGeocoderResponse {
    @SerializedName("geo_lat") String lat;
    @SerializedName("geo_lon") String lon;

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
