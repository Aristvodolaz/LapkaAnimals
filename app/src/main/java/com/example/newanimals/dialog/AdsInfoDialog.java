package com.example.newanimals.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newanimals.R;
import com.example.newanimals.db.AdsDataKt;
import com.example.newanimals.fragment.BaseFragment;
import com.example.newanimals.presenter.GetIndoAdsDialogPresenter;
import com.example.newanimals.utils.WaitDialogUtils;
import com.example.newanimals.view.GetInfoAdsDialogView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AdsInfoDialog extends BottomSheetDialogFragment implements GetInfoAdsDialogView {
    public static final String NAME = "name";
    public static final String DATE = "date";
    public static final String MESTO = "mesto";
    private WaitDialogUtils waitDialogUtils;
    Unbinder unbinder;
    private GetIndoAdsDialogPresenter presenter;
    int layoutId() {
        return R.layout.ads_layouf_dialog_bottom;
    }


    public static AdsInfoDialog newInstance(String name_anim, String datePropazhi, String mestoPropazhi) {
        Bundle args = new Bundle();
        args.putString(NAME, name_anim);
        args.putString(MESTO, mestoPropazhi);
        args.putString(DATE, datePropazhi);
        AdsInfoDialog fragment = new AdsInfoDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(layoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        return view;
    }
    void initViews() {
        presenter = new GetIndoAdsDialogPresenter(this);
        waitDialogUtils.show(getFragmentManager(),"wait");
        presenter.getInfoAds(getArguments().getString(NAME),getArguments().getString(DATE), getArguments().getString(MESTO));
    }

    @Override
    public void showInfo(AdsDataKt data) {

    }

    @Override
    public void showError(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }


}
