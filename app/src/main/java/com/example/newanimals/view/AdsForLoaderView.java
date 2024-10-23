package com.example.newanimals.view;

import com.example.newanimals.db.AdsData;
import com.example.newanimals.db.AdsDataKt;

import java.util.List;

public interface AdsForLoaderView {
    void getAds(List<AdsData> dataList);
}
