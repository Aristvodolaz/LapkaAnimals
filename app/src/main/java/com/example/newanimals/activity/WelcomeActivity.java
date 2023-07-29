package com.example.newanimals.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.newanimals.R;
import com.example.newanimals.db.PersonData;
import com.example.newanimals.fragment.AdsBoardFragment;
import com.example.newanimals.fragment.AdsCreateFragment;
import com.example.newanimals.fragment.MapFargmentKt;
import com.example.newanimals.fragment.PersonalFragment;
import com.example.newanimals.presenter.PersonDataPresenter;
import com.example.newanimals.utils.SPHelper;
import com.example.newanimals.view.PersonDataView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yandex.mapkit.MapKitFactory;

import butterknife.BindView;

public class WelcomeActivity extends BaseActivity implements PersonDataView {
    @BindView(R.id.user)
    ImageView userBtn;
    @BindView(R.id.user_active)
    ImageView userActive;
    @BindView(R.id.maps)
    ImageView mapBtn;
    @BindView(R.id.map_active)
    ImageView mapActive;
    @BindView(R.id.add_full)
    ImageView adsBtn;
    @BindView(R.id.ads_active)
    ImageView adsActive;
    @BindView(R.id.add_ads)
    ImageView addBtn;
    @BindView(R.id.free_hands)
    ImageView volonterBtn;
    @BindView(R.id.hands_active)
    ImageView handsActive;

    @BindView(R.id.ads_btn)
    LinearLayout ads_btn;
    @BindView(R.id.maps_btn)
    LinearLayout maps_btn;
    @BindView(R.id.hands_btn)
    LinearLayout hands_btn;
    @BindView(R.id.person_btn)
    LinearLayout person_btn;

    private PersonDataPresenter presenter;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void initViews(@Nullable Bundle saveInstanceState) {
        MapKitFactory.setApiKey("12f079b1-d006-468f-b2a0-d0ea01443347");
        MapKitFactory.initialize(this);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        replaceFragment(MapFargmentKt.newInstance(), true);
        presenter = new PersonDataPresenter(this);
        if(user!=null){
            presenter.getData(user.getEmail());
        }
        person_btn.setOnClickListener(v->{
           replaceFragment(PersonalFragment.newInstance(), true);
           userBtn.setVisibility(View.GONE);
           userActive.setVisibility(View.VISIBLE);

           adsActive.setVisibility(View.GONE);
           adsBtn.setVisibility(View.VISIBLE);

           volonterBtn.setVisibility(View.VISIBLE);
           handsActive.setVisibility(View.GONE);

           mapActive.setVisibility(View.GONE);
           mapBtn.setVisibility(View.VISIBLE);
//           person_btn.setBackgroundResource(R.drawable.user_active);
//           mapBtn.setBackgroundResource(R.drawable.map);
//            volonterBtn.setBackgroundResource(R.drawable.hands);
//            adsBtn.setBackgroundResource(R.drawable.ads_full);
        });
        maps_btn.setOnClickListener(v->{
            replaceFragment(MapFargmentKt.newInstance(), true);
//            replaceFragment(MapsFragment.newInstance(), true);
            userBtn.setVisibility(View.VISIBLE);
            userActive.setVisibility(View.GONE);

            adsBtn.setVisibility(View.VISIBLE);
            adsActive.setVisibility(View.GONE);

            volonterBtn.setVisibility(View.VISIBLE);
            handsActive.setVisibility(View.GONE);

            mapBtn.setVisibility(View.GONE);
            mapActive.setVisibility(View.VISIBLE);
        });
        ads_btn.setOnClickListener(v->{
            replaceFragment(AdsBoardFragment.newInstance(), true);
            userBtn.setVisibility(View.VISIBLE);
            userActive.setVisibility(View.GONE);

            adsBtn.setVisibility(View.GONE);
            adsActive.setVisibility(View.VISIBLE);

            volonterBtn.setVisibility(View.VISIBLE);
            handsActive.setVisibility(View.GONE);

            mapBtn.setVisibility(View.VISIBLE);
            mapActive.setVisibility(View.GONE);
        });
        hands_btn.setOnClickListener(v->{
//            replaceFragment(PersonalFragment.newInstance(), false);
            userBtn.setVisibility(View.VISIBLE);
            userActive.setVisibility(View.GONE);

            adsBtn.setVisibility(View.VISIBLE);
            adsActive.setVisibility(View.GONE);

            volonterBtn.setVisibility(View.GONE);
            handsActive.setVisibility(View.VISIBLE);

            mapBtn.setVisibility(View.VISIBLE);
            mapActive.setVisibility(View.GONE);
        });
        addBtn.setOnClickListener(v->{
            replaceFragment(AdsCreateFragment.newInstance(), true);
//            startActivity(new Intent(this, AddAdsActivity.class));
//            person_btn.setBackgroundResource(R.drawable.user);
//            mapBtn.setBackgroundResource(R.drawable.map);
//            volonterBtn.setBackgroundResource(R.drawable.hands);
//            adsBtn.setBackgroundResource(R.drawable.ads_full);
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
