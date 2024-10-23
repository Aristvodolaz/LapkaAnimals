package com.example.newanimals.presenter;

import androidx.annotation.NonNull;

import com.example.newanimals.db.AdsData;
import com.example.newanimals.db.AdsDataKt;
import com.example.newanimals.network.Const;
import com.example.newanimals.utils.ReadRXFirebaseUtil;
import com.example.newanimals.utils.SPHelper;
import com.example.newanimals.view.AdsForLoaderView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetAddsForLoaderPresenter {
    private AdsForLoaderView view;
    private List<AdsData> ads = new ArrayList<>();
    private List<AdsData> adsLtn = new ArrayList<>();


    public GetAddsForLoaderPresenter(AdsForLoaderView view) {
        this.view = view;
    }

    public void getAdsInfo() {
        FirebaseDatabase database = FirebaseDatabase.getInstance(Const.URL);
        DatabaseReference adsDB = database.getReference().child("AdsData");
        adsDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    AdsData adsData = childSnapshot.getValue(AdsData.class);
                    if (adsData != null) {
                        ads.add(adsData);
                    }
                }
               if(!ads.isEmpty()) {
                   for (int i = 0; i < ads.size(); i++) {
                       boolean isZone = checkZone(Double.parseDouble(ads.get(i).getLat()), Double.parseDouble(ads.get(i).getLon()));
                       if (isZone) adsLtn.add(ads.get(i));
                   }
               }
               if(!adsLtn.isEmpty() && adsLtn!=null) view.getAds(adsLtn);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private boolean checkZone(double lat, double lon){
        double result = 111.2 * Math.sqrt((lon - SPHelper.getLon())*(lon - SPHelper.getLon())
                + (lat - SPHelper.getLat())*Math.cos(Math.PI*lon/180)*(lat - SPHelper.getLat())*Math.cos(Math.PI*lon/180)) * 1000;
        return !(result > 250);
    }
    private static void processLarge(List<AdsDataKt> data){
        System.out.println("Total data count: " + data.size());
    }
}
