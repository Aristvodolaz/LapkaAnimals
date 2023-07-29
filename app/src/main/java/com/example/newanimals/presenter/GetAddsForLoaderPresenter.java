package com.example.newanimals.presenter;

import com.example.newanimals.db.AdsDataKt;
import com.example.newanimals.utils.ReadRXFirebaseUtil;
import com.example.newanimals.utils.SPHelper;
import com.example.newanimals.view.AdsForLoaderView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetAddsForLoaderPresenter {
    private AdsForLoaderView view;
    private List<AdsDataKt> ads = new ArrayList<>();
    private List<AdsDataKt> adsLat = new ArrayList<>();


    public GetAddsForLoaderPresenter(AdsForLoaderView view) {
        this.view = view;
    }

    public void getAdsInfo(){
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
//
//                    @Override
//                    public void onNext(DataSnapshot dataSnapshot) {
//                        AdsData data = dataSnapshot.getValue(AdsData.class);
//                        if(data!=null){
//                            ads.add(data);
//                            for(int i = 0 ; i < ads.size(); i++){
//                                boolean isZone = checkZone(ads.get(i).getLat(),ads.get(i).getLon());
//                                if(isZone){
//                                    adsLat.add(ads.get(i));
//                                }
//                            }
//                            if(adsLat!=null)
//                                view.getAds(adsLat);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        processLarge(ads);
//                    }
//                });
    }

    private boolean checkZone(double lat, double lon){
        double result = 111.2 * Math.sqrt((lon - SPHelper.getLon())*(lon - SPHelper.getLon())
                + (lat - SPHelper.getLat())*Math.cos(Math.PI*lon/180)*(lat - SPHelper.getLat())*Math.cos(Math.PI*lon/180)) * 1000;
        if(result>250)
            return false;
        else return true;
    }
    private static void processLarge(List<AdsDataKt> data){
        System.out.println("Total data count: " + data.size());
    }
}
