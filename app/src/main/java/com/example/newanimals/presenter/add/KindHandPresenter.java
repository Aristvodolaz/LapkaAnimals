package com.example.newanimals.presenter.add;

import android.net.Uri;

import com.example.newanimals.db.AdsData;
import com.example.newanimals.utils.UploadPhotoStorageUtil;
import com.example.newanimals.utils.WriteRXFirebaseUtil;
import com.example.newanimals.view.add.KindHandView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class KindHandPresenter {

    private KindHandView view;

    public KindHandPresenter(KindHandView view) {
        this.view = view;
    }

    public void postDataInDB(AdsData data){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference ref = database.collection("AdsData");

        WriteRXFirebaseUtil.saveDataToFirebase(ref, database)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver(){
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        view.showMessage("Объявление успешно опубликовано");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showMessage(e.getLocalizedMessage());
                    }
                });
    }

    public void uploadPhoto(Uri img, String file){
        UploadPhotoStorageUtil.uploadePhoto(img, file).subscribe(
                ()-> {view.showMessage("Фотография загружена");},
                error ->{
                    view.showMessage(error.getLocalizedMessage());
                }
        );
    }
}
