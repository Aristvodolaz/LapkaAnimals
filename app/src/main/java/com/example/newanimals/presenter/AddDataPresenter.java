package com.example.newanimals.presenter;

import android.net.Uri;
import android.util.Log;

import com.example.newanimals.db.AdsDataKt;
import com.example.newanimals.model.DaDataModel;
import com.example.newanimals.network.request.GeocoderAddressBody;
import com.example.newanimals.network.response.DaDataGeocoderResponse;
import com.example.newanimals.utils.UploadPhotoStorageUtil;
import com.example.newanimals.utils.WriteRXFirebaseUtil;
import com.example.newanimals.view.AddAdsView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;

import io.reactivex.rxjava3.schedulers.Schedulers;
import rx.Subscriber;


public class AddDataPresenter {
    private AddAdsView view;
    private DaDataModel model = new DaDataModel();

    public AddDataPresenter(AddAdsView view) {
        this.view = view;
    }

    public void writeData(AdsDataKt data){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("AdsData");

        WriteRXFirebaseUtil.setValue(ref,data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        view.sendMessage("Данные успешно добавлены!");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.sendMessage(e.getLocalizedMessage());
                    }
                });
    }

    public void uploadPhoto(Uri img, String file){
        UploadPhotoStorageUtil.uploadePhoto(img, file).subscribe(
                ()-> {view.onUploadSuccess("Фотография загружена");},
                error ->{
                    view.onUploadError(error.getLocalizedMessage());
                }
        );
    }


    public void getCoordinates(List<String> data){
        model.getCoordinates(data).subscribe(new Subscriber<List<DaDataGeocoderResponse>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                view.sendMessage(e.getLocalizedMessage());
            }

            @Override
            public void onNext(List<DaDataGeocoderResponse> daDataGeocoderResponses) {
                if(daDataGeocoderResponses!=null){
                for (int i = 0 ; i < daDataGeocoderResponses.size();i++){
                    view.getCoordinates(daDataGeocoderResponses.get(i).getLat(),daDataGeocoderResponses.get(i).getLon());
                }
                } else
                    Log.e("ddd", "warning");
            }
        });
    }
}
