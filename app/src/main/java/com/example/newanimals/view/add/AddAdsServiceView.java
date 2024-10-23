package com.example.newanimals.view.add;

public interface AddAdsServiceView {
    void message(String msg);
    void successMessage(String msg);
    void successAddAds(String msg);
    void getCoordinates(String lat, String lon);
}
