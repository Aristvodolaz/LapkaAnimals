package com.example.newanimals.utils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WriteRXFirebaseUtil {

    public static Completable setValue(final DatabaseReference ref, final Object value) {
        return Completable.create(
                new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                ref.setValue(value, (databaseError, databaseReference) -> {
                    if (databaseError == null) {
                        emitter.onComplete();
                    } else {
                        emitter.onError(databaseError.toException());
                    }
                });
            }
        }).subscribeOn(Schedulers.io());
    }
}
