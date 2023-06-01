package com.example.newanimals.view;

import com.example.newanimals.db.AdsData;

import java.util.List;

public interface GetDataView {
    void getInfoAds(List<AdsData>data);
    void errorMessage(String err);
}
