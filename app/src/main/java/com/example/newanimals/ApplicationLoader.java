package com.example.newanimals;
import android.content.Context;


import androidx.multidex.MultiDexApplication;

import com.example.newanimals.db.AdsData;
import com.example.newanimals.db.AdsDataKt;
import com.example.newanimals.presenter.GetAddsForLoaderPresenter;
import com.example.newanimals.utils.NotificationHelper;
import com.example.newanimals.view.AdsForLoaderView;

import java.util.List;


public class ApplicationLoader extends MultiDexApplication implements AdsForLoaderView {
    public static ApplicationLoader instance;
    private GetAddsForLoaderPresenter presenter;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        presenter = new GetAddsForLoaderPresenter(this);
        presenter.getAdsInfo();
    }

    public static ApplicationLoader getInstance() {
        return instance;
    }

    @Override
    public void getAds(List<AdsData> dataList) {
        for(int i = 0 ; i< dataList.size(); i++) {
            NotificationHelper.showNotification(instance, "Будтье внимательней,\n вы находитесь в радиусе пропавшего животного.",
                    dataList.get(i).getTypeAnimals() + "\n" + dataList.get(i).getName_animals() + "\nДата пропажи:"
                            + dataList.get(i).getDate_lose() + "\nАдрес пропажи:" + dataList.get(i).getAddress(), dataList.get(i).getImgUrl());
        }
    }
}
