package com.example.newanimals.fragment;

import android.widget.Button;

import com.example.newanimals.R;
import com.example.newanimals.activity.MainActivity;
import com.example.newanimals.fragment.auth_and_reg.LoginFragmentKt;

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
        vhodBtn.setOnClickListener(vi->{
            ((MainActivity)getActivity()).replaceFragment(LoginFragmentKt.newInstance(),true);
        });
        regBtn.setOnClickListener(v->{
            ((MainActivity)getActivity()).replaceFragment(ChooseRegFragment.newInstance(),true);
        });
    }
}
