package com.example.newanimals.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.newanimals.R;
import com.example.newanimals.fragment.AdsBoardFragment;
import com.example.newanimals.fragment.AdsCreateFragment;
import com.example.newanimals.fragment.PersonalFragment;
import com.example.newanimals.fragment.chat.chats_mvvm.ChatListFragment;
import com.example.newanimals.presenter.reg_and_auth.LoginUserPresenter;
import com.example.newanimals.view.reg_and_auth.LoginUserView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yandex.mapkit.MapKitFactory;
import com.example.newanimals.fragment.MapFragment;

import butterknife.BindView;

public class WelcomeActivity extends BaseActivity implements LoginUserView {
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

    private LoginUserPresenter presenter;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void initViews(@Nullable Bundle saveInstanceState) {
        FirebaseApp.initializeApp(this);
        MapKitFactory.setApiKey("12f079b1-d006-468f-b2a0-d0ea01443347");
        MapKitFactory.initialize(this);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        presenter = new LoginUserPresenter(this);
        replaceFragment(MapFragment.newInstance(), true);
        if(user!=null){
            presenter.getUserInfo(user.getEmail());
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

        });
        maps_btn.setOnClickListener(v->{
//            replaceFragment(MapFargmentKt.newInstance(), true);
            replaceFragment(MapFragment.newInstance(), true);

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
            userBtn.setVisibility(View.VISIBLE);
            userActive.setVisibility(View.GONE);

            adsBtn.setVisibility(View.VISIBLE);
            adsActive.setVisibility(View.GONE);

            volonterBtn.setVisibility(View.GONE);
            handsActive.setVisibility(View.VISIBLE);

            mapBtn.setVisibility(View.VISIBLE);
            mapActive.setVisibility(View.GONE);
            replaceFragment(ChatListFragment.Companion.newInstance(),true);
        });
        addBtn.setOnClickListener(v->{
            replaceFragment(AdsCreateFragment.newInstance(), true);
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

//    @Override
//    public void message(String str) {
//        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void getType(String type) {

    }
}
