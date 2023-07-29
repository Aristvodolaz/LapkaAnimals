package com.example.newanimals.network;

import com.example.newanimals.db.AdsDataKt;
import com.example.newanimals.network.request.GeocoderAddressBody;
import com.example.newanimals.network.response.DaDataGeocoderResponse;
import com.example.newanimals.network.response.DaDataResponse;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {

    @GET(Const.ABS)
    Observable<AdsDataKt> getAds(@Query("session") String session);

    @GET(Const.ADDRESS_DADATA)
    Observable<DaDataResponse> getAddressDaData(@Query("query") String query);

    @POST(Const.ADDRESS_DADATA)
    Observable<List<DaDataGeocoderResponse>> getCoordinates(@Body List<String> body);
}
