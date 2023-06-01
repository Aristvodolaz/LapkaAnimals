package com.example.newanimals.fragment;

import android.os.Bundle;
import android.widget.Button;

import com.example.newanimals.R;
import com.example.newanimals.activity.MainActivity;

import butterknife.BindView;

public class StartAppFragment extends BaseFragment{
    public static StartAppFragment newInstance() {
        return new StartAppFragment();
    }
    @BindView(R.id.vhod)
    Button vhodBtn;
    @BindView(R.id.regist)
    Button regBtn;
    @Override
    protected int layoutId() {
        return R.layout.start_layout;
    }

    @Override
    protected void initViews() {
        super.initViews();
        vhodBtn.setOnClickListener(v->{
            ((MainActivity)getActivity()).replaceFragment(LoginFragment.newInstance(), false);
        });
        regBtn.setOnClickListener(v->{
            ((MainActivity)getActivity()).replaceFragment(ChooseRegFragment.newInstance(), false);
        });
    }
}
