package com.example.newanimals.fragment;

import android.os.Bundle;

import com.example.newanimals.R;

public class AdsFragment extends  BaseFragment{
    public static AdsFragment newInstance() {
        return new AdsFragment();
    }

    @Override
    protected int layoutId() {
        return R.layout.ads_fragment_layout;
    }
}
