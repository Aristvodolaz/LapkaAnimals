package com.example.newanimals.presenter;

import com.example.newanimals.model.DaDataModel;
import com.example.newanimals.network.response.DaDataResponse;
import com.example.newanimals.view.AddressView;

import rx.Subscriber;

public class AddressPresenter {
    private AddressView view;

    private DaDataModel model = new DaDataModel();

    public AddressPresenter(AddressView view) {
        this.view = view;
    }
//    public void getAddressWithLatLon(float lat, float lon){
//        model.getAddressWithLatLon(lat, lon).subscribe(new Subscriber<GeolocateResponse>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                view.errorMessage(e.getLocalizedMessage());
//            }
//
//            @Override
//            public void onNext(GeolocateResponse geolocateResponse) {
////                if(geolocateResponse!=null)
////                view.getAddressLatLon(geolocateResponse.getSuggestionsData().getValue());
////                else view.errorMessage("Ошибка получения данных");
//            }
//        });
//    }

    public void getFullNameAddress(String address){
        model.getAddressDaData(address).subscribe(new Subscriber<DaDataResponse>() {
            @Override public void onCompleted() { }

            @Override
            public void onError(Throwable e) {
                view.errorMessage("Ошибка получения данных");
            }

            @Override
            public void onNext(DaDataResponse response) {
                view.getAddress(response.getSuggestions());
            }
        });
    }
}