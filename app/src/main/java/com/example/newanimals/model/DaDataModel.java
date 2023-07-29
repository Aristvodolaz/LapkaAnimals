package com.example.newanimals.model;

import com.example.newanimals.network.BaseDataProvider;
import com.example.newanimals.network.request.GeocoderAddressBody;
import com.example.newanimals.network.response.DaDataGeocoderResponse;
import com.example.newanimals.network.response.DaDataResponse;

import java.util.List;

import rx.Observable;

public class DaDataModel extends BaseDataProvider {

    public Observable<DaDataResponse> getAddressDaData(String address){
        return serviceDaDataAddress.getAddressDaData(address).compose(applySchedulers());
    }

    public Observable<List<DaDataGeocoderResponse>> getCoordinates(List<String> addressBody){
        return serviceDaDataCoordinates.getCoordinates(addressBody).compose(applySchedulers());
    }
}
