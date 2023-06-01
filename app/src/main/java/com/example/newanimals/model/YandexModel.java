package com.example.newanimals.model;



import com.example.newanimals.network.BaseDataProvider;
import com.example.newanimals.network.Const;
import com.example.newanimals.network.response.GeocoderResponse;

import rx.Observable;

public class YandexModel extends BaseDataProvider {

    public Observable<GeocoderResponse> getAdress(String address){
        return serviceYandex.getAddress(Const.YANDEX_API_FORMAT, Const.YANDEX_API_KEY,address).compose(applySchedulers());
    }
}
