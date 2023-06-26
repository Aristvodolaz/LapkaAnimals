package com.example.newanimals.presenter;

import com.example.newanimals.db.AdsData;
import com.example.newanimals.utils.ReadRXFirebaseUtil;
import com.example.newanimals.view.GetDogView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetDogPresenter {
    private GetDogView view;
    private List<AdsData> list = new ArrayList<>();
    private List<AdsData> dog = new ArrayList<>();

    public GetDogPresenter(GetDogView view) {
        this.view = view;
    }

    public void getInfo(){
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
//                            if(list.get(i).getTypeAnimals().equals("Собака")){
//                                dog.add(list.get(i));
//                            }
//                        }
//                        view.getAds(dog);
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
//                        processLarge(dog);
//                    }
//                });
    }
    private static void processLarge(List<AdsData> data){
        System.out.println("Total data count: " + data.size());
    }
}
