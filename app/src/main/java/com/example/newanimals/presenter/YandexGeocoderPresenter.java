package com.example.newanimals.presenter;

import com.example.newanimals.model.YandexModel;
import com.example.newanimals.network.response.GeocoderResponse;
import com.example.newanimals.view.YandexGeocoderView;

import java.util.List;

import rx.Subscriber;

public class YandexGeocoderPresenter {
    private YandexGeocoderView view;
    private YandexModel model;
    private List<String> list;

    public YandexGeocoderPresenter(YandexGeocoderView view) {
        this.view = view;
    }

    public void getTextAddress(String text){
        model.getAdress(text).subscribe(new Subscriber<GeocoderResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                    view.meessageError(e.getLocalizedMessage());
            }

            @Override
            public void onNext(GeocoderResponse geocoderResponse) {
                if(geocoderResponse !=null){
                    List<GeocoderResponse.FeatureMember> features = (List<GeocoderResponse.FeatureMember>)
                            geocoderResponse.getResponse().getGeoObjectCollection().getFeatureMember();
                    for(GeocoderResponse.FeatureMember featureMember:features){
                        String formated = featureMember.getGeoObject().getMetaDataProperty().getGeocoderMetaData().getAddress().getFormatted();
                        list.add(formated);
                    }
                    view.addressText(list);
                }
            }
        });

    }
}
