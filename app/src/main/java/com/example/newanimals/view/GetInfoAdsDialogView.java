package com.example.newanimals.view;

import com.example.newanimals.db.AdsDataKt;

import java.util.List;

public interface GetInfoAdsDialogView {
    void showInfo(AdsDataKt data);
    void showError(String str);
}
