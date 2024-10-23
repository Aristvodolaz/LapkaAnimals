package com.example.newanimals.fragment.add_ads_animals;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.newanimals.R;
import com.example.newanimals.activity.AddAdsActivity;
import com.example.newanimals.activity.AdsActivity;
import com.example.newanimals.activity.WelcomeActivity;
import com.example.newanimals.fragment.BaseFragment;
import com.example.newanimals.utils.SPHelper;

import butterknife.BindView;

public class ChooseTypeAnimalFragment extends BaseFragment {
    public static ChooseTypeAnimalFragment newInstance() {
        return new ChooseTypeAnimalFragment();
    }
    @BindView(R.id.back_arrow)
    ImageView backArrow;
    @BindView(R.id.name_animals)
    EditText nameAnimals;
    @BindView(R.id.next_btn)
    Button nextBtn;
    @BindView(R.id.cat)
    CheckBox cat;
    @BindView(R.id.dog)
    CheckBox dog;
    @BindView(R.id.other)
    CheckBox other;
    @BindView(R.id.girl) CheckBox girl;
    @BindView(R.id.man) CheckBox man;

    @Override
    protected void initViews() {
        super.initViews();
        backArrow.setOnClickListener(l->{
            getActivity().getSupportFragmentManager().popBackStack();
        });
        nextBtn.setOnClickListener(l->{
            if(!SPHelper.AdsHelper.getTypeAnimals().equals("") && !SPHelper.AdsHelper.getPolAnimals().equals("") && !nameAnimals.getText().toString().equals("")) {
                SPHelper.AdsHelper.setNameAnimals(nameAnimals.getText().toString());
                ((AddAdsActivity)getActivity()).replaceFragment(OpisanieFragment.newInstance(), true);
            }else Toast.makeText(getContext(), "Выберите значение!",Toast.LENGTH_SHORT).show();
        });

        checkTypeAnimals();
        checkPolAnimals();
    }

    private void checkPolAnimals() {
        girl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(girl.isChecked()){
                    man.setClickable(false);
                    SPHelper.AdsHelper.setPolAnimals("0");
                }else {
                    man.setClickable(true);
                    nextBtn.setClickable(false);
                }
            }
        });
        man.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(man.isChecked()){
                    girl.setClickable(false);
                    SPHelper.AdsHelper.setPolAnimals("1");
                }else {
                    girl.setClickable(true);
                    nextBtn.setClickable(false);
                }
            }
        });
    }

    private void checkTypeAnimals() {
        cat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cat.isChecked()){
                    dog.setClickable(false);
                    other.setClickable(false);
                    SPHelper.AdsHelper.setTypeAnimals("0");
                }else {
                    dog.setClickable(true);
                    other.setClickable(true);
                    nextBtn.setClickable(false);
                }
            }
        });
        dog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(dog.isChecked()){
                    cat.setClickable(false);
                    other.setClickable(false);
                    SPHelper.AdsHelper.setTypeAnimals("1");
                }else {
                    cat.setClickable(true);
                    other.setClickable(true);
                    nextBtn.setClickable(false);
                }
            }
        });
        other.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(other.isChecked()){
                    dog.setClickable(false);
                    cat.setClickable(false);
                    SPHelper.AdsHelper.setTypeAnimals("2");
                }else {
                    dog.setClickable(true);
                    cat.setClickable(true);
                    nextBtn.setClickable(false);
                }
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.choose_type_animal_fragment;
    }
}
