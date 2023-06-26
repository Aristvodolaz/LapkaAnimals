package com.example.newanimals.presenter;

import com.example.newanimals.db.AdsData;
import com.example.newanimals.db.PersonData;
import com.example.newanimals.utils.ReadRXFirebaseUtil;
import com.example.newanimals.view.PersonDataView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PersonDataPresenter {
    private PersonDataView view;
    private List<PersonData> list = new ArrayList<>();

    public PersonDataPresenter(PersonDataView view) {
        this.view = view;
    }

    public void getData(String login){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataRef = database.getReference("Users");

//        ReadRXFirebaseUtil.observeValueEvent(dataRef)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<DataSnapshot>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        // Выполняется при подписке
//                    }
//
//                    @Override
//                    public void onNext(DataSnapshot dataSnapshot) {
//                        PersonData data = dataSnapshot.getValue(PersonData.class);
//                        if(data!=null){
//                            list.add(data);
//                        }
//                        for (int i = 0 ; i< list.size(); i++){
//                            if(list.get(i).getLogin().equals(login)){
//                                view.getData(list.get(i));
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.message(e.getLocalizedMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        processLarge(list);
//                    }
//                });
    }

    private static void processLarge(List<PersonData> data){
        System.out.println("Total data count: " + data.size());
    }
}
