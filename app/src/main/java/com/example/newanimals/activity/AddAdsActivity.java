package com.example.newanimals.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.newanimals.R;
import com.example.newanimals.fragment.AddAdsFindHomeFragment;

public class AddAdsActivity extends BaseActivity{
    @Override
    protected void initViews(@Nullable Bundle saveInstanceState) {
        String type = getIntent().getStringExtra("type");
        if ((type.equals("house"))){
            replaceFragment(AddAdsFindHomeFragment.newInstance(), true);
        }
    }

    @Override
    protected int layoutResId() {
        return R.layout.add_activity_layout;
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
}
