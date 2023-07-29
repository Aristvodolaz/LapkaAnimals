package com.example.newanimals.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.newanimals.R;
import com.example.newanimals.fragment.StartAppFragment;
import com.example.newanimals.utils.SPHelper;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends BaseActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    double lon = 0, lat = 0;
    Handler handler = new Handler();
    Runnable runnable;
    LocationManager locationManager;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 287;
    private FusedLocationProviderClient mFusedLocationClient;
    private Task<LocationSettingsResponse> task;
    private LocationCallback mLocationCallback;
    private static final int CODE_DIALOG = 5;
    private static final int CODE_ACTIVITY = 7;

    @Override
    protected void initViews(@Nullable Bundle saveInstanceState) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user != null)
            startActivity(new Intent(this, WelcomeActivity.class));
//            startActivity(new Intent(this,TestActivity.class));
        else
            replaceFragment(StartAppFragment.newInstance(), false);

    }

    private void getPosition() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isGPS) {
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 7);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        } else {
            LocationRequest mLocationRequest = createLocationRequest();
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
            SettingsClient client = LocationServices.getSettingsClient(MainActivity.this);
            task = client.checkLocationSettings(builder.build())
                    .addOnSuccessListener(MainActivity.this, locationSettingsResponse -> {
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
                    });
        }


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                lon = locationResult.getLocations().get(0).getLongitude();
                lat = locationResult.getLocations().get(0).getLatitude();

                SPHelper.setLon((float) lon);
                SPHelper.setLat((float) lat);

            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Разрешение на использование данных GPS получено", Toast.LENGTH_LONG).show();
                    LocationRequest mLocationRequest = createLocationRequest();
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
                    SettingsClient client = LocationServices.getSettingsClient(MainActivity.this);
                    task = client.checkLocationSettings(builder.build())
                            .addOnSuccessListener(MainActivity.this, locationSettingsResponse -> {
                                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
                            });
                } else {
                    Toast.makeText(this, "Разрешение на получение данных не получено", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_ACTIVITY && resultCode == 0) {
            makeLocaleSettings(CODE_DIALOG);
        } else if(requestCode == CODE_DIALOG && resultCode == -1) {
            makeLocaleSettings(-1);
        }
        else {
            Toast.makeText(this, "Разрешение на получение данных не получено", Toast.LENGTH_SHORT).show();

        }
    }
    public void makeLocaleSettings(int requestCode) {
        LocationRequest mLocationRequest = createLocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        task = client.checkLocationSettings(builder.build())
                .addOnSuccessListener(this, locationSettingsResponse -> {
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
                    Log.e("wwwww", " location setting is success");
                }).addOnFailureListener(this, e -> {
                    int statusCode = ((ApiException) e).getStatusCode();
                    switch (statusCode) {
                        case CommonStatusCodes.RESOLUTION_REQUIRED:
                            if(requestCode != -1) {
                                try {
                                    ResolvableApiException resolvable = (ResolvableApiException) e;
                                    resolvable.startResolutionForResult(MainActivity.this, requestCode);
                                } catch (IntentSender.SendIntentException sendEx) { }
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                });
    }

    protected  LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
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