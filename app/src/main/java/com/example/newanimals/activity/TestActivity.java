package com.example.newanimals.activity;

import android.Manifest;
import android.content.Context;
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
import android.app.Activity;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.newanimals.R;
import com.example.newanimals.utils.SPHelper;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;

import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

/**
 * This example shows a map and moves the camera to the specified point.
 * Be sure to request the necessary permissions.
 */
public class TestActivity extends Activity {
    /**
     * Substitute "your_api_key" with a valid API key.
     *  You can get the key at https://developer.tech.yandex.ru/
     */
    private final String MAPKIT_API_KEY = "12f079b1-d006-468f-b2a0-d0ea01443347";
//    private final Point TARGET_LOCATION = new Point(SPHelper.getLat(),SPHelper.getLon());
    private Double lat , lon;
    private MapView mapView;
    Handler handler = new Handler();
    Runnable runnable;
    MapObjectCollection mapObject;
    Bitmap bitmap;
    private static final int MY_PERMISSION_REQUEST_LOCATION = 859;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        MapKitFactory.setApiKey(MAPKIT_API_KEY);

        MapKitFactory.initialize(this);
        // Creating a MapView.
        setContentView(R.layout.maps_fragment_layout);
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            try {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location!=null){
                    lon = location.getLongitude();
                    lat = location.getLatitude();
                    SPHelper.setLon(lon.floatValue());
                    SPHelper.setLat(lat.floatValue());
//                    PlacemarkMapObject placemarkMapObject = mapObject.addPlacemark(new Point(lat,lon));
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
                if (location!=null) {
                    lon = location.getLongitude();
                    lat = location.getLatitude();
                    SPHelper.setLon(lon.floatValue());
                    SPHelper.setLat(lat.floatValue());
//                    PlacemarkMapObject placemarkMapObject = mapObject.addPlacemark(new Point(lat,lon));
                }
            }
        };
        super.onCreate(savedInstanceState);
        mapView = (MapView)findViewById(R.id.mapview);
        if (lat != null && lon!=null) {
            mapView.getMap().move(
                    new CameraPosition(new Point(lat, lon), 18.0f, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 5),
                    null);
        } else     mapView.getMap().move(
                new CameraPosition(new Point(53.243400, 34.363991), 14.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 5),
                null);
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.naple);
    }
    private void getPosition(){
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            try {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location!=null){
                    lon = location.getLongitude();
                    lat = location.getLatitude();
                    SPHelper.setLon(lon.floatValue());
                    SPHelper.setLat(lat.floatValue());
                    mapView.getMap().getMapObjects().addPlacemark(new Point(lat,lon),ImageProvider.fromBitmap(drawSimpleBitmap()));
//                    PlacemarkMapObject placemarkMapObject = mapObject.addPlacemark(new Point(lat,lon));
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
                SPHelper.setLon(lon.floatValue());
                SPHelper.setLat(lat.floatValue());
            }
        };
    }
    public Bitmap drawSimpleBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(24, 24, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        // отрисовка плейсмарка
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#E39D32"));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(24 / 2, 24 / 2, 24 / 2, paint);
        return bitmap;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
        handler.postDelayed( runnable = () -> {
            handler.postDelayed(runnable, 5000);
            getPosition();
        }, 5000);
    }
}