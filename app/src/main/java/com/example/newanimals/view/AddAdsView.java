package com.example.newanimals.view;

public interface AddAdsView {
     void sendMessage(String str);

    void onUploadSuccess(String str);

    void onUploadError(String localizedMessage);

    void getCoordinates(String lat, String lon);
}
