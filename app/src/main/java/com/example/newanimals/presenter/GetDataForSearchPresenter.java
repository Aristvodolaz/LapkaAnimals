package com.example.newanimals.presenter;

import com.example.newanimals.db.AdsData;
import com.example.newanimals.utils.ReadRXFirebaseUtil;
import com.example.newanimals.view.GetDataForSearchView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetDataForSearchPresenter {
    private GetDataForSearchView view;
    private List<AdsData> list = new ArrayList<>();
    private List<AdsData> forDog = new ArrayList<>();
    private List<AdsData> forCat =new ArrayList<>();

    public GetDataForSearchPresenter(GetDataForSearchView view) {
        this.view = view;
    }
    public void getCatData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataRef = database.getReference("AdsData");
//
//        ReadRXFirebaseUtil.observeValueEvent(dataRef)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<DataSnapshot>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        // Выполняется при подписке
//                    }
//                    @Override
//                    public void onNext(DataSnapshot dataSnapshot) {
//                        AdsData data = dataSnapshot.getValue(AdsData.class);
//                        if(data!=null){
//                            list.add(data);
//                        }
//                        for(int i = 0 ; i < list.size();i++){
//                            if((list.get(i).getTypeAnimals().equals("Кошка")
//                                    || list.get(i).getTypeAnimals().equals("Кот"))
//                                    && list.get(i).getType().equals("10")){
//                                forCat.add(list.get(i));
//                            }
//                        }
//                        view.getAds(forCat);
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        view.message(e.getLocalizedMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        // Выполняется по завершении
//                        processLarge(forCat);
//                    }
//                });
    }


    public void getDogData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataRef = database.getReference("AdsData");

//        ReadRXFirebaseUtil.observeValueEvent(dataRef)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<DataSnapshot>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        // Выполняется при подписке
//                    }
//                    @Override
//                    public void onNext(DataSnapshot dataSnapshot) {
//                        AdsData data = dataSnapshot.getValue(AdsData.class);
//                        if(data!=null){
//                            list.add(data);
//                        }
//                        for(int i = 0 ; i < list.size();i++){
//                            if(list.get(i).getTypeAnimals().equals("Собака")
//                                    && list.get(i).getType().equals("10")){
//                                forDog.add(list.get(i));
//                            }
//                        }
//                        view.getAds(forDog);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.message(e.getLocalizedMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        // Выполняется по завершении
//                        processLarge(forDog);
//                    }
//                });
    }
    private static void processLarge(List<AdsData> data){
        System.out.println("Total data count: " + data.size());
    }
}
