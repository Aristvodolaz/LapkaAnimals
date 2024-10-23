package com.example.newanimals.fragment;

import android.widget.Button;
import android.widget.ImageView;

import com.example.newanimals.R;
import com.example.newanimals.activity.MainActivity;
import com.example.newanimals.fragment.auth_and_reg.RegistrationFragment;

import butterknife.BindView;

public class ChooseRegFragment extends  BaseFragment{
    public static ChooseRegFragment newInstance() {
        return new ChooseRegFragment();
    }
    @BindView(R.id.findBtn)
    Button findBtn;
    @BindView(R.id.helpBtn)
    Button helpBtn;
    @BindView(R.id.workBtn)
    Button workBtn;
    @BindView(R.id.back_arrow)
    ImageView backArrow;
    @Override
    protected int layoutId() {
        return R.layout.choose_reg_layout;
    }

    @Override
    protected void initViews() {
        super.initViews();
        findBtn.setOnClickListener(v->{
            ((MainActivity)getActivity()).replaceFragment(RegistrationFragment.newInstance("0","user"), false);
        });
        helpBtn.setOnClickListener(v->{
            ((MainActivity)getActivity()).replaceFragment(RegistrationFragment.newInstance("1", "work_user"), false);
        });
        workBtn.setOnClickListener(v->{
            ((MainActivity)getActivity()).replaceFragment(RegistrationFragment.newInstance("2","free_user"), false);
        });

        backArrow.setOnClickListener(l->{getActivity().getSupportFragmentManager().popBackStack();});
    }
}
