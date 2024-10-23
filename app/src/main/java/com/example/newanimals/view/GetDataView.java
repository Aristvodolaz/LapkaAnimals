package com.example.newanimals.view;

import com.example.newanimals.db.AdsData;
import com.example.newanimals.db.AdsDataKt;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface GetDataView {
//    void getInfoAds(List<AdsDataKt> data);
    void errorMessage(String err);

    void getInfoAds(List<AdsData> ads);
}
