package com.example.newanimals.fragment;

import android.os.Bundle;

import com.example.newanimals.R;

public class PersonalFragment extends BaseFragment{
    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }
    @Override
    protected int layoutId() {
        return R.layout.personal_fragment_layout;
    }
}
