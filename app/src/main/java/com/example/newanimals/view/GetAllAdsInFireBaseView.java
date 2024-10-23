package com.example.newanimals.view;

import com.example.newanimals.db.AdsData;

import java.util.List;

public interface GetAllAdsInFireBaseView {
    void getAds(List<AdsData> data);
    void messageShow(String msg);
}
