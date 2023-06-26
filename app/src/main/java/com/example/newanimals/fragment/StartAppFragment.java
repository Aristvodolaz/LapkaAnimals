package com.example.newanimals.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.newanimals.R;
import com.example.newanimals.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


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
