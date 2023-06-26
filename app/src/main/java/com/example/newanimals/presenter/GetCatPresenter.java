package com.example.newanimals.presenter;

import com.example.newanimals.db.AdsData;
import com.example.newanimals.utils.ReadRXFirebaseUtil;
import com.example.newanimals.view.GetCatView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetCatPresenter {

    private GetCatView view;
    private List<AdsData> list = new ArrayList<>();
    private List<AdsData> cat = new ArrayList<>();

    public GetCatPresenter(GetCatView view) {
        this.view = view;
    }

    public void getInfo() {

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference dataRef = database.collection("AdsData");

        ReadRXFirebaseUtil.observeValueEvent(dataRef)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QuerySnapshot>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull QuerySnapshot documentSnapshots) {
                        List<AdsData> data = new ArrayList<>();
                        for(DocumentSnapshot document: documentSnapshots.getDocuments()){
                            data.add(document.toObject(AdsData.class));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private static void processLarge(List<AdsData> data){
        System.out.println("Total data count: " + data.size());
    }
}
