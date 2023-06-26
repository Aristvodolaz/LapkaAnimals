package com.example.newanimals.presenter;

import android.net.Uri;

import com.example.newanimals.db.PersonData;
import com.example.newanimals.utils.UploadPhotoStorageUtil;
import com.example.newanimals.utils.WriteRXFirebaseUtil;
import com.example.newanimals.view.AddUsersView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddUsersPresenter {

    private AddUsersView view;

    public AddUsersPresenter(AddUsersView view) {
        this.view = view;
    }

    public void usersInDB(PersonData data){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users");

        WriteRXFirebaseUtil.setValue(ref,data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onComplete() {
//                        view.sendMessage("Данные успешно добавлены!");
                        view.createUser();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                       e.getLocalizedMessage();
                    }
                });
    }
}
