package com.example.newanimals.fragment;

import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.newanimals.adapter.AddsAdapter;
import com.example.newanimals.db.AdsData;
import com.example.newanimals.dialog.WaitDialog;
import com.example.newanimals.presenter.GetDogPresenter;
import com.example.newanimals.utils.WaitDialogUtils;
import com.example.newanimals.view.GetDogView;

import java.util.List;

public class AdsDogFragment extends BaseFragment implements GetDogView {
    private WaitDialogUtils waitDialogUtils;
    private GetDogPresenter presenter;

    @Override
    public void onResume() {
        super.onResume();
        presenter = new GetDogPresenter(this);
        waitDialogUtils.show(getFragmentManager(),"wait");
        presenter.getInfo();
    }

    @Override
    protected int layoutId() {
        return 0;
    }

    @Override
    public void message(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getAds(List<AdsData> data) {
        waitDialogUtils.dismiss();
        AddsAdapter adapter = new AddsAdapter(getContext(), data);
//        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        rv.setAdapter(adapter);
    }


}
