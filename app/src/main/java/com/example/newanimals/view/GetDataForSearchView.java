package com.example.newanimals.view;

import com.example.newanimals.db.AdsData;

import java.util.List;

public interface GetDataForSearchView {
    void message(String str);
    void getAds(List<AdsData> data);
}
