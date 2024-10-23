package com.example.newanimals.presenter.add;

import android.util.Log;

import com.example.newanimals.db.AdsData;
import com.example.newanimals.model.DaDataModel;
import com.example.newanimals.network.response.DaDataGeocoderResponse;
import com.example.newanimals.utils.SPHelper;
import com.example.newanimals.utils.WriteRXFirebaseUtil;
import com.example.newanimals.view.AddAdsInDBView;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import rx.Subscriber;

public class AddAdsInDBPresenter {
    private AddAdsInDBView view;
    private DaDataModel model = new DaDataModel();

    public AddAdsInDBPresenter(AddAdsInDBView view) {
        this.view = view;
    }

    public void getCoordiates(List<String> data){
        model.getCoordinates(data).subscribe(new Subscriber<List<DaDataGeocoderResponse>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                view.message(e.getLocalizedMessage());
            }

            @Override
            public void onNext(List<DaDataGeocoderResponse> daDataGeocoderResponses) {
                if(daDataGeocoderResponses!=null){
                    for (int i = 0 ; i < daDataGeocoderResponses.size();i++){
                        view.getCoordinates(daDataGeocoderResponses.get(i).getLat(),daDataGeocoderResponses.get(i).getLon());
                    }
                } else
                    Log.e("ddd", "warning");
            }
        });
    }

    private String getDate(){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        return dateFormat.format(currentDate);
    }
    private String getTime(){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(currentDate);
    }
    public void postDataInDb(AdsData data){
        String databaseURL = "https://lapka2-5144b-default-rtdb.europe-west1.firebasedatabase.app";
        FirebaseDatabase database = FirebaseDatabase.getInstance(databaseURL);
        DatabaseReference reference = database.getReference("AdsData");
        String uniqueKey = data.age+data.days+data.typeAnimals+data.color+ getTrueType(getTime()) + getTrueType(getDate())+"10";

        reference.child(uniqueKey).setValue(data, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if (error == null) {
                    view.successMessage("Объявление успешно опубликовано");
                } else {
                    view.message(error.getMessage());
                }
            }
        });
    }

    private String getTrueType(String time) {
        return time.replace(".", "_");
    }
}
