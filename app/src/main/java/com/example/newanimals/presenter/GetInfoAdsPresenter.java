package com.example.newanimals.presenter;

import com.example.newanimals.db.AdsData;
import com.example.newanimals.utils.ReadRXFirebaseUtil;
import com.example.newanimals.view.GetDataView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;


public class GetInfoAdsPresenter {

    private GetDataView getDataView;
    List<AdsData> ads = new ArrayList<>();

    public GetInfoAdsPresenter(GetDataView getDataView) {
        this.getDataView = getDataView;
    }
    public void getAdsInfo(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataRef = database.getReference("AdsData");

        ReadRXFirebaseUtil.observeValueEvent(dataRef)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataSnapshot>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // Выполняется при подписке
                    }

                    @Override
                    public void onNext(DataSnapshot dataSnapshot) {
                        AdsData data = dataSnapshot.getValue(AdsData.class);
                        if(data!=null){
                            ads.add(data);
                        }
                        getDataView.getInfoAds(ads);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getDataView.errorMessage(e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        processLarge(ads);
                    }
                });
    }

    private static void processLarge(List<AdsData>data){
        System.out.println("Total data count: " + data.size());
    }

}
