package com.example.newanimals.fragment;

import android.os.Bundle;

import com.example.newanimals.R;

public class PermitFragment extends BaseFragment{
    public static PermitFragment newInstance() {
        return new PermitFragment();
    }
    @Override
    protected int layoutId() {
        return R.layout.permit_fragment;
    }

    @Override
    protected void initViews() {
        super.initViews();

    }
}
