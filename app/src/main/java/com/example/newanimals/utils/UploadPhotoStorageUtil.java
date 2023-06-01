package com.example.newanimals.utils;

import android.net.Uri;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;


public class UploadPhotoStorageUtil {

    private FirebaseStorage storage;
    private static StorageReference storageReference;

    public  UploadPhotoStorageUtil() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    public static Completable uploadePhoto(Uri image, String fileName){
        StorageReference photoref = storageReference.child(fileName);

        return Completable.create(emmiter->{
            UploadTask uploadTask = photoref.putFile(image);
            uploadTask.addOnCompleteListener(taskSnapshot-> {
                if (taskSnapshot.isSuccessful()) {
                    photoref.getDownloadUrl().addOnSuccessListener(uri->
                            SPHelper.setUrlPhoto(image.toString())).addOnFailureListener(e->{
                    });
                    emmiter.onComplete();
                }
                else emmiter.onError(taskSnapshot.getException());
            });
        }).subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread());

    }
}
