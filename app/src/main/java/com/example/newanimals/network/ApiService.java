package com.example.newanimals.network;

import com.example.newanimals.db.AdsData;
import com.example.newanimals.network.response.GeocoderResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {

    @GET(Const.ABS)
    Observable<AdsData> getAds(@Query("session") String session);

    @GET
    Observable<GeocoderResponse> getAddress(@Query("format") String format, @Query("apikey") String key, @Query("geocode") String geocode);
}
