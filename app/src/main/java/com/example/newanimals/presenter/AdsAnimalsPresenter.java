package com.example.newanimals.presenter;

import com.example.newanimals.db.AdsData;
import com.example.newanimals.utils.ReadRXFirebaseUtil;
import com.example.newanimals.view.AdsAnimalsView;

import java.util.ArrayList;
import java.util.List;

public class AdsAnimalsPresenter {

    public AdsAnimalsView view;
    private List<AdsData> list = new ArrayList<>();
    private List<AdsData> cat = new ArrayList<>();
    public AdsAnimalsPresenter(AdsAnimalsView view) {
        this.view = view;
    }


}
