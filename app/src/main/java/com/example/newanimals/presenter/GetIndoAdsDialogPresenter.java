package com.example.newanimals.presenter;

import androidx.annotation.NonNull;

import com.example.newanimals.db.AdsDataKt;
import com.example.newanimals.network.Const;
import com.example.newanimals.utils.ReadRXFirebaseUtil;
import com.example.newanimals.utils.SPHelper;
import com.example.newanimals.view.GetInfoAdsDialogView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.C;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetIndoAdsDialogPresenter {

    private GetInfoAdsDialogView view;
    private List<AdsDataKt> ads;

    public GetIndoAdsDialogPresenter(GetInfoAdsDialogView view) {
        this.view = view;
    }

    public void getInfoAds(String name, String date, String mesto){

//        DatabaseReference dataRef = database.getReference("AdsData");
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
//                        AdsData data = dataSnapshot.getValue(AdsData.class);
//                        if(data!=null){
//                            ads.add(data);
//                        }
//                        for(int i = 0 ; i < ads.size(); i++){
//                            if(ads.get(i).getAddress().equals(mesto)
//                                    && ads.get(i).getDate_lose().equals(date)
//                                    && ads.get(i).getName_animals().equals(name))
//                                view.showInfo(ads.get(i));
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.showError(e.getLocalizedMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        processLarge(ads);
//                    }
//                });
    }

    private static void processLarge(List<AdsDataKt> data){
        System.out.println("Total data count: " + data.size());
    }

}
