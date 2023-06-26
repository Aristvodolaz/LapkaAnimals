package com.example.newanimals.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.newanimals.R;
import com.example.newanimals.fragment.StartAppFragment;
import com.example.newanimals.utils.SPHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends BaseActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    double lon = 0, lat = 0;
    Handler handler = new Handler();
    Runnable runnable;
    LocationManager locationManager;
    private static final int MY_PERMISSION_REQUEST_LOCATION = 859;
    @Override
    protected void initViews(@Nullable Bundle saveInstanceState) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user != null)
            startActivity(new Intent(this,WelcomeActivity.class));
//            startActivity(new Intent(this,TestActivity.class));
        else
            replaceFragment(StartAppFragment.newInstance(), false);

    }

    private void getPosition(){
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            try {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location!=null){
                    lon = location.getLongitude();
                    lat = location.getLatitude();
                    SPHelper.setLon((float) lon);
                    SPHelper.setLat((float) lat);
                } else Toast.makeText(this, "Ошибка получения местоположения", Toast.LENGTH_SHORT).show();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        } else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
        }
        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lon = location.getLongitude();
                lat = location.getLatitude();
                SPHelper.setLon((float) lon);
                SPHelper.setLat((float) lat);
            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, locationListener);
    }
    @Override
    protected void onStart() {
        super.onStart();
        handler.postDelayed( runnable = () -> {
            handler.postDelayed(runnable, 5000);
            getPosition();
        }, 5000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_main;
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