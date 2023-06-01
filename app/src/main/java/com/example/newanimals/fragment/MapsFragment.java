package com.example.newanimals.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.newanimals.R;
import com.example.newanimals.db.AdsData;
import com.example.newanimals.db.TypeAdressData;
import com.example.newanimals.presenter.GetInfoAdsPresenter;
import com.example.newanimals.utils.SPHelper;
import com.example.newanimals.utils.WaitDialogUtils;
import com.example.newanimals.view.GetDataView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MapsFragment extends BaseFragment implements LocationListener, GetDataView {
    private static final int MY_PERMISSION_REQUEST_LOCATION = 859;
    private GetInfoAdsPresenter presenter;
    private Map map;
    private Double lat , lon;
    private List<LatLng> list = new ArrayList<>();
    private List<TypeAdressData> dataType;
    private WaitDialogUtils dialogFragment;
    private MapObjectCollection mapObject;
    @BindView(R.id.mapview)
    MapView mapView;

    public static MapsFragment newInstance() {
        return new MapsFragment();
    }

    @Override
    protected void initViews() {
        super.initViews();
        MapKitFactory.setApiKey("5ec304e2-e5bd-4261-a58b-5dfd3351fad7");
        MapKitFactory.initialize(getContext());
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            try {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location!=null){
                    lon = location.getLongitude();
                    lat = location.getLatitude();
                    SPHelper.setLon(lon.floatValue());
                    SPHelper.setLat(lat.floatValue());
                } else Toast.makeText(getContext(), "Ошибка получения местоположения", Toast.LENGTH_SHORT).show();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        } else{
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
        }
        final  LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lon = location.getLongitude();
                lat = location.getLatitude();
                SPHelper.setLon(lon.floatValue());
                SPHelper.setLat(lat.floatValue());
            }
        };
        presenter = new GetInfoAdsPresenter(this);
        dialogFragment = newInstance().dialogFragment;
        dialogFragment.show(getFragmentManager(),"wait");
        presenter.getAdsInfo();

        mapView.onStart();
        mapView.getMap()
                .move(new CameraPosition(new Point(lat, lon),14.0f, 0.0f, 0.0f),
                        new Animation(Animation.Type.SMOOTH, 5),
                        null);

    }

    private void onMapLoaded(Map loadedMap) {
        map = loadedMap;
        mapObject = map.getMapObjects();
        if(!list.isEmpty() && !dataType.isEmpty()){
        for(int i= 0 ; i <list.size();i++) {
            for (TypeAdressData data : dataType) {
                PlacemarkMapObject placemarkMapObject = mapObject.addPlacemark(new Point(list.get(i).latitude, list.get(i).longitude));
//                if(data.getType().equals("10"))
//                    placemarkMapObject.setIcon();
            }
        }} else Toast.makeText(getContext(), "Активные объявления отсутствуют!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();;
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }


    @Override
    public void getInfoAds(List<AdsData> data) {
        dialogFragment.dismiss();
        List<LatLng> list = new ArrayList<>();
        for (int i = 0 ; i<data.size(); i++){
            list.add(new com.google.android.gms.maps.model.LatLng(data.get(i).getLat(), data.get(i).getLon()));
            dataType.add(new TypeAdressData(data.get(i).getType(), data.get(i).getAddress()));
        }
    }

    @Override
    public void errorMessage(String err) {
        Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int layoutId() {
        return R.layout.maps_fragment_layout;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        lon = location.getLongitude();
        SPHelper.setLon(lon.floatValue());
        lat = location.getLatitude();
        SPHelper.setLat(lat.floatValue());
    }

}
