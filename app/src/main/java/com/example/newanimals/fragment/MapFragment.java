package com.example.newanimals.fragment;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.newanimals.R;
import com.example.newanimals.activity.MainActivity;
import com.example.newanimals.db.AdressData;
import com.example.newanimals.db.AdsData;
import com.example.newanimals.db.AdsDataKt;
import com.example.newanimals.presenter.GetInfoAdsPresenterKt;
import com.example.newanimals.utils.SPHelper;
import com.example.newanimals.utils.WaitDialogUtils;
import com.example.newanimals.view.GetDataView;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.GeoPoint;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements LocationListener, GetDataView {

    private MapView mapView;
    private Map map;
    private Bitmap bitmap;
    private Handler handler = new Handler();
    private Runnable runnable;
    private MapObjectCollection mapObject;
    private GetInfoAdsPresenterKt presenter;
    private List<AdressData> dataType = new ArrayList<>();
    private PlacemarkMapObject placeObject;
    private ValueAnimator pointAnimator;
    private PlacemarkMapObject placemark;
    private Point startPoint;
    private Point endPoint;
    double lon, lat;
    LocationManager locationManager;
    private PlacemarkMapObject previousPlaceObject;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 287;
    private FusedLocationProviderClient mFusedLocationClient;
    private Task<LocationSettingsResponse> task;
    private LocationCallback mLocationCallback;
    private static final int CODE_DIALOG = 5;
    private static final int CODE_ACTIVITY = 7;
    private List<LatLng> list = new ArrayList();

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.maps_fragment_layout, container, false);
        mapView = rootView.findViewById(R.id.mapview);
        mapView.onStart();

        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        requestLocationUpdates();

    }

    private void requestLocationUpdates() {
        LocationRequest mLocationRequest = createLocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(requireContext());

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                lon = locationResult.getLocations().get(0).getLongitude();
                lat = locationResult.getLocations().get(0).getLatitude();
                SPHelper.setLon((float) lon);
                SPHelper.setLat((float) lat);
                if(lon!=SPHelper.getLon() && lat!=SPHelper.getLat())
                    createPositionUser(lat, lon);

            }
        };

        task = client.checkLocationSettings(builder.build())
                .addOnSuccessListener(locationSettingsResponse -> {
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
                });

    }

    private void createPositionUser(double lat1, double lon2) {
        if (lat1 != 0.0 && lon2 != 0.0) {
            mapView.getMap().move(
                    new CameraPosition(new Point(lat1, lon2), 18.0f, 0.0f, 0.0f),
                    new Animation(Animation.Type.LINEAR, 5f),
                    null
            );

            if (previousPlaceObject != null) {
                mapView.getMap().getMapObjects().remove(previousPlaceObject);
            }

            previousPlaceObject = mapView.getMap().getMapObjects().addPlacemark(new Point(lat1, lon2), ImageProvider.fromBitmap(drawSimpleBitmap()));

            if (pointAnimator != null) {
                pointAnimator.cancel();
            }

            startPoint = previousPlaceObject.getGeometry();
            endPoint = new Point(lat1, lon2);

            pointAnimator = ValueAnimator.ofFloat(0, 1);
            pointAnimator.setDuration(5000);
            pointAnimator.addUpdateListener(animation -> {
                float fraction = animation.getAnimatedFraction();
                double animatedLat = startPoint.getLatitude() + (endPoint.getLatitude() - startPoint.getLatitude()) * fraction;
                double animatedLon = startPoint.getLongitude() + (endPoint.getLongitude() - startPoint.getLongitude()) * fraction;

                Point animatedPoint = new Point(animatedLat, animatedLon);
                previousPlaceObject.setGeometry(animatedPoint);
            });
            pointAnimator.start();
        } else {
            mapView.getMap().move(
                    new CameraPosition(new Point(53.243400, 34.363991), 14.0f, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 5f),
                    null
            );
        }
    }



    @Override
    public void onLocationChanged(Location location) {
        lon = location.getLongitude();
        SPHelper.setLon((float) lon);
        lat = location.getLatitude();
        SPHelper.setLat((float) lat);
    }

    private void getPosition() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        boolean isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isGPS) {
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 7);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        } else {
            LocationRequest mLocationRequest = createLocationRequest();
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
            SettingsClient client = LocationServices.getSettingsClient(getContext());
            task = client.checkLocationSettings(builder.build())
                    .addOnSuccessListener(locationSettingsResponse -> {
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
                    });
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
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
                    Toast.makeText(getContext(), "Разрешение на использование данных GPS получено", Toast.LENGTH_LONG).show();
                    LocationRequest mLocationRequest = createLocationRequest();
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
                    SettingsClient client = LocationServices.getSettingsClient(getContext());
                    task = client.checkLocationSettings(builder.build())
                            .addOnSuccessListener(locationSettingsResponse -> {
                                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
                            });
                } else {
                    Toast.makeText(getContext(), "Разрешение на получение данных не получено", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_ACTIVITY && resultCode == 0) {
            makeLocaleSettings(CODE_DIALOG);
        } else if (requestCode == CODE_DIALOG && resultCode == -1) {
            makeLocaleSettings(-1);
        } else {
            Toast.makeText(getContext(), "Разрешение на получение данных не получено", Toast.LENGTH_SHORT).show();
        }
    }

    public void makeLocaleSettings(int requestCode) {
        LocationRequest mLocationRequest = createLocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(getContext());
        task = client.checkLocationSettings(builder.build())
                .addOnSuccessListener(locationSettingsResponse -> {
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
                    Log.e("wwwww", " location setting is success");
                }).addOnFailureListener(e -> {
                    int statusCode = ((ApiException) e).getStatusCode();
                    switch (statusCode) {
                        case CommonStatusCodes.RESOLUTION_REQUIRED:
                            if (requestCode != -1) {
                                try {
                                    ResolvableApiException resolvable = (ResolvableApiException) e;
                                    resolvable.startResolutionForResult(getActivity(), requestCode);
                                } catch (IntentSender.SendIntentException sendEx) {
                                }
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                });
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    private Bitmap drawSimpleBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(24, 24, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#E39D32"));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(24 / 2, 24 / 2, 24 / 2, paint);
        return bitmap;
    }

    private void onMapLoaded(Map loadedMap) {
        map = loadedMap;
        ImageProvider img;
        mapObject = map.getMapObjects();
        if (!list.isEmpty() && !dataType.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                for (AdressData data : dataType) {
                    if (data.getType().equals("10") || data.getType().equals("11")) {
                        img = ImageProvider.fromResource(getContext(), R.drawable.service);
                        mapObject.addPlacemark(new Point(list.get(i).latitude, list.get(i).longitude), img);
                    } else if (data.getType().equals("0") || data.getType().equals("1") || data.getType().equals("2")) {
                        img = ImageProvider.fromResource(getContext(), R.drawable.animals);
                        mapObject.addPlacemark(new Point(list.get(i).latitude, list.get(i).longitude), img);
                    }
                }
            }
        } else {
            Toast.makeText(getContext(), "Активные объявления отсутствуют!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void errorMessage(String err) {
        Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getInfoAds(List<AdsData> data) {
        List<LatLng> lists = new ArrayList<>();
        List<AdressData> datas = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            lists.add(new LatLng(Double.parseDouble(data.get(i).getLat()), Double.parseDouble(data.get(i).getLon())));
            datas.add(new AdressData(data.get(i).getType(), data.get(i).getAddress()));
        }
        list = lists;
        dataType = datas;

        onMapLoaded(mapView.getMap());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (runnable != null) {
                    handler.postDelayed(runnable, 5000);
                }
                presenter = new GetInfoAdsPresenterKt(MapFragment.this);
                presenter.getAdsInfo();
                getPosition();
            }
        }, 5000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
