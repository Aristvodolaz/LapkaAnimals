package com.example.newanimals.utils;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ReadRXFirebaseUtil {

    public static Observable<DataSnapshot> observeValueEvent(final DatabaseReference ref) {
        return Observable.create(new ObservableOnSubscribe<DataSnapshot>() {
            @Override
            public void subscribe(final ObservableEmitter<DataSnapshot> emitter) throws Exception {
                final ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        emitter.onNext(dataSnapshot);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        emitter.onError(error.toException());
                    }
                };
                ref.addValueEventListener(eventListener);
                // Отписка от слушателя при отмене подписки
                emitter.setCancellable(() -> ref.removeEventListener(eventListener));
            }
        }).subscribeOn(Schedulers.io());
    }
}
