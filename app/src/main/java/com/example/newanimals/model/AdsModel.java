package com.example.newanimals.model;

import com.example.newanimals.db.AdsData;
import com.example.newanimals.network.BaseDataProvider;
import com.example.newanimals.network.Const;
import com.example.newanimals.network.response.GeocoderResponse;

import rx.Observable;

public class AdsModel extends BaseDataProvider {


    //todo this empty for example
    public Observable<AdsData> getStatuses(String type) {
        return service.getAds(type).compose(applySchedulers());
    }
}
