package com.example.newanimals.fragment.add_ads_animals;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newanimals.R;
import com.example.newanimals.activity.AddAdsActivity;
import com.example.newanimals.activity.WelcomeActivity;
import com.example.newanimals.fragment.BaseFragment;
import com.example.newanimals.utils.SPHelper;
import com.example.newanimals.view.AddAdsInDBView;

import butterknife.BindView;

public class OpisanieFragment extends BaseFragment {
    public static OpisanieFragment newInstance() {
        return new OpisanieFragment();
    }
    @BindView(R.id.poroda)
    EditText poroda;
    @BindView(R.id.opisanie)
    EditText opisanie;
    @BindView(R.id.back_arrow)
    ImageView backArrow;
    @BindView(R.id.next_btn)
    Button nextBtn;
    @BindView(R.id.color)
    EditText okras;

    @BindView(R.id.age)
    EditText age;

    @Override
    protected void initViews() {
        super.initViews();

        backArrow.setOnClickListener(l->{getActivity().getSupportFragmentManager().popBackStack();});
        nextBtn.setOnClickListener(l->{
            if(!poroda.getText().toString().equals("") && !opisanie.getText().toString().equals("")
                    && !okras.getText().toString().equals("") && !age.getText().toString().equals("")){

                SPHelper.AdsHelper.setPorodaAnimals(poroda.getText().toString());
                SPHelper.AdsHelper.setOpisanie(poroda.getText().toString());
                SPHelper.AdsHelper.setOkras(okras.getText().toString());
                SPHelper.AdsHelper.setAgeAnimals(age.getText().toString());

                ((AddAdsActivity)getActivity()).replaceFragment(AddressFragment.newInstance(), true);
            } else Toast.makeText(getContext(), "заполните все поля!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.opisanie_fragment;
    }
}
