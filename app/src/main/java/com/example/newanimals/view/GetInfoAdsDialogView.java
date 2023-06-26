package com.example.newanimals.view;

import com.example.newanimals.db.AdsData;

import java.util.List;

public interface GetInfoAdsDialogView {
    void showInfo(AdsData data);
    void showError(String str);
}
