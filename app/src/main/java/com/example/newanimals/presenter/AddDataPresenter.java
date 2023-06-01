package com.example.newanimals.presenter;

import android.net.Uri;

import com.example.newanimals.db.AdsData;
import com.example.newanimals.utils.UploadPhotoStorageUtil;
import com.example.newanimals.utils.WriteRXFirebaseUtil;
import com.example.newanimals.view.AddAdsView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;

import io.reactivex.rxjava3.schedulers.Schedulers;


public class AddDataPresenter {
    private AddAdsView view;

    public AddDataPresenter(AddAdsView view) {
        this.view = view;
    }

    public void writeData(AdsData data){
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
}
