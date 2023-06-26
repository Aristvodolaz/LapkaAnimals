package com.example.newanimals.fragment;

import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newanimals.R;
import com.example.newanimals.db.AdsData;
import com.example.newanimals.presenter.GetDataForSearchPresenter;
import com.example.newanimals.presenter.GetInfoAdsPresenter;
import com.example.newanimals.utils.WaitDialogUtils;
import com.example.newanimals.view.GetDataForSearchView;
import com.example.newanimals.view.GetDataView;

import java.util.List;

import butterknife.BindView;

public class AnimalsAdsPoteryashkiFragment extends BaseFragment implements GetDataForSearchView {
    @BindView(R.id.catBtn)
    Button catBtn;
    @BindView(R.id.dogBtn)
    Button dogBtn;
    @BindView(R.id.rv)
    RecyclerView rv;
    private GetDataForSearchPresenter presenter;
    private WaitDialogUtils dialogFragment;

    @Override
    protected void initViews() {
        super.initViews();
        presenter = new GetDataForSearchPresenter(this);
        catBtn.setOnClickListener(v->{
            dialogFragment.show(getFragmentManager(),"wait");
            presenter.getCatData();});

        dogBtn.setOnClickListener(v->{
            dialogFragment.show(getFragmentManager(),"wait");
            presenter.getDogData();});
    }

    @Override
    protected int layoutId() {
        return R.layout.animals_ads_layout;
    }


    @Override
    public void message(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getAds(List<AdsData> data) {
        dialogFragment.dismiss();
//        AddsAdapter adapter = new AddsAdapter(getContext(), data);
//        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        rv.setAdapter(adapter);
    }
}
