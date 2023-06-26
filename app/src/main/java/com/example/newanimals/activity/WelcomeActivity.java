package com.example.newanimals.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.newanimals.R;
import com.example.newanimals.db.PersonData;
import com.example.newanimals.fragment.AdsBoardFragment;
import com.example.newanimals.fragment.AdsCreateFragment;
import com.example.newanimals.fragment.AdsFragment;
import com.example.newanimals.fragment.MapsFragment;
import com.example.newanimals.fragment.PersonalFragment;
import com.example.newanimals.presenter.PersonDataPresenter;
import com.example.newanimals.utils.SPHelper;
import com.example.newanimals.view.PersonDataView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;

public class WelcomeActivity extends BaseActivity implements PersonDataView {
    @BindView(R.id.user)
    ImageView userBtn;
    @BindView(R.id.maps)
    ImageView mapBtn;
    @BindView(R.id.add_full)
    ImageView adsBtn;
    @BindView(R.id.add_ads)
    ImageView addBtn;
    @BindView(R.id.free_hands)
    ImageView volonterBtn;

    private PersonDataPresenter presenter;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void initViews(@Nullable Bundle saveInstanceState) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        presenter = new PersonDataPresenter(this);
        if(user!=null){
            presenter.getData(user.getEmail());
        }
        userBtn.setOnClickListener(v->{
           replaceFragment(PersonalFragment.newInstance(), true);
        });
        mapBtn.setOnClickListener(v->{
            replaceFragment(MapsFragment.newInstance(), true);
        });
        adsBtn.setOnClickListener(v->{
            replaceFragment(AdsBoardFragment.newInstance(), true);
        });
        volonterBtn.setOnClickListener(v->{
//            replaceFragment(PersonalFragment.newInstance(), false);
        });
        addBtn.setOnClickListener(v->{
            replaceFragment(AdsCreateFragment.newInstance(), true);
//            startActivity(new Intent(this, AddAdsActivity.class));
        });
    }

    @Override
    protected int layoutResId() {
        return R.layout.welcome_main_layout;
    }

    @Override
    protected int titleResId() {
        return 0;
    }
    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment, fragment.getClass().getSimpleName());
        if (addToBackStack) fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    @Override
    public void message(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getData(PersonData data) {
        SPHelper.setCity(data.getCity());
        SPHelper.setPhone(data.getPhone());
        SPHelper.setName(data.getName());
        SPHelper.setSurname(data.getSurname());
        SPHelper.setType(data.getType());
        SPHelper.setLogin(data.getLogin());
        SPHelper.setNametype(data.getName_type());
    }
}
