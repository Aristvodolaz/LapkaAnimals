package com.example.newanimals.fragment;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.newanimals.R;
import com.example.newanimals.activity.MainActivity;

import butterknife.BindView;

public class PermitFragment extends BaseFragment{
    public static PermitFragment newInstance() {
        return new PermitFragment();
    }
    @BindView(R.id.back_arrow)
    ImageView back_arrow;
    @Override
    protected int layoutId() {
        return R.layout.permit_fragment;
    }

    @Override
    protected void initViews() {
        super.initViews();
        back_arrow.setOnClickListener(v->{
            ((MainActivity)getActivity()).replaceFragment(StartAppFragment.newInstance(),false);
        });
    }
}
