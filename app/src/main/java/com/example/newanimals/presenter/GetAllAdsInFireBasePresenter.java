package com.example.newanimals.presenter;

import com.example.newanimals.db.AdsData;
import com.example.newanimals.db.AdsDataKt;
import com.example.newanimals.utils.ReadRXFirebaseUtil;
import com.example.newanimals.view.GetAllAdsInFireBaseView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class GetAllAdsInFireBasePresenter {

    private GetAllAdsInFireBaseView view;

    public GetAllAdsInFireBasePresenter(GetAllAdsInFireBaseView view) {
        this.view = view;
    }
    public void getDataAdsFireBaseWithQuery(String queryType) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference reference = firestore.collection("AdsData");


        ReadRXFirebaseUtil.observeValueEvent(reference)
                .subscribe(
                        queryDocumentSnapshots -> {
                            List<AdsData> dataKts = new ArrayList<>();

                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                AdsData adsDataKt = documentSnapshot.toObject(AdsData.class);
                                if (adsDataKt.getType().equals(queryType)) {
                                    dataKts.add(adsDataKt);
                                }
                            }
                            view.getAds(dataKts);
                        },
                        throwable -> view.messageShow(throwable.getMessage())
                );
    }
}
