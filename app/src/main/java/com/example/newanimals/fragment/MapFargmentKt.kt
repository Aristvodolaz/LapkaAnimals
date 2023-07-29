package com.example.newanimals.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.*
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.newanimals.R
import com.example.newanimals.db.AdressData
import com.example.newanimals.db.AdsDataKt
import com.example.newanimals.db.TypeAdressData
import com.example.newanimals.presenter.GetInfoAdsPresenterKt
import com.example.newanimals.utils.SPHelper
import com.example.newanimals.utils.WaitDialogUtils
import com.example.newanimals.view.GetDataView
import com.google.android.gms.maps.model.LatLng
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

class MapFargmentKt: Fragment(), LocationListener, GetDataView {

    private lateinit var mapView: MapView
    private lateinit var map: Map
    private var lat: Double? = null
    private var lon: Double? = null
    var bitmap: Bitmap? = null
    private val dialogFragment: WaitDialogUtils? = null
    private val MY_PERMISSION_REQUEST_LOCATION = 859
    var handler = Handler()
    var runnable: Runnable? = null
    private var mapObject: MapObjectCollection? = null
    private var presenter: GetInfoAdsPresenterKt? = null
    var dataType by mutableStateOf<List<AdressData>>(emptyList())
    private var placeObject: PlacemarkMapObject? = null
    var list by mutableStateOf<List<LatLng>>(emptyList())
    companion object{
        @JvmStatic
        fun newInstance(): MapFargmentKt = MapFargmentKt()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.maps_fragment_layout, container, false)
        mapView = rootView.findViewById(R.id.mapview)
        mapView.onStart()
        val locationManager =
            context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location != null) {
                    lon = location.longitude
                    lat = location.latitude
                    SPHelper.setLon(lon!!.toFloat())
                    SPHelper.setLat(lat!!.toFloat())
                } else Toast.makeText(
                    context,
                    "Ошибка получения местоположения",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        } else {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSION_REQUEST_LOCATION
                )
            }
        }
        val locationListener =
            LocationListener { location ->
                if (location != null) {
                    lon = location.longitude
                    lat = location.latitude
                    SPHelper.setLon(lon!!.toFloat())
                    SPHelper.setLat(lat!!.toFloat())
                }
            }

        if (lat != null && lon != null) {
            mapView.map.move(
                CameraPosition(Point(lat!!, lon!!), 18.0f, 0.0f, 0.0f),
                Animation(Animation.Type.SMOOTH, 5f),
                null
            )
        } else mapView.map.move(
            CameraPosition(Point(53.243400, 34.363991), 14.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 5f),
            null
        )
        bitmap = BitmapFactory.decodeResource(this.resources, R.drawable.naple)

        return rootView
    }

    override fun onLocationChanged(location: Location) {
        lon = location.longitude
        SPHelper.setLon(lon!!.toFloat())
        lat = location.latitude
        SPHelper.setLat(lat!!.toFloat())
    }
    private fun getPosition() {
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location != null) {
                    lon = location.longitude
                    lat = location.latitude
                    SPHelper.setLon(lon!!.toFloat())
                    SPHelper.setLat(lat!!.toFloat())
                    mapView.map.mapObjects.addPlacemark(
                        Point(
                            lat!!,
                            lon!!
                        ), ImageProvider.fromBitmap(drawSimpleBitmap())
                    )
                    //                    PlacemarkMapObject placemarkMapObject = mapObject.addPlacemark(new Point(lat,lon));
                } else Toast.makeText(
                    context,
                    "Ошибка получения местоположения",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSION_REQUEST_LOCATION
            )
        }
        val locationListener =
            LocationListener { location ->
                lon = location.longitude
                lat = location.latitude
                SPHelper.setLon(lon!!.toFloat())
                SPHelper.setLat(lat!!.toFloat())
            }
    }
    override fun getInfoAds(data: List<AdsDataKt>?) {
        dialogFragment?.dismiss()
        val lists: MutableList<LatLng> = ArrayList()
        val datas: MutableList<AdressData> = ArrayList()
        for (i in data!!.indices) {
            lists.add(LatLng(data!![i].lat.toDouble(), data!![i].lon.toDouble()))
            datas.add(AdressData(data[i].type, data[i].address))
//            dataType.ad(TypeAdressData(data!![i].type, data!![i].address))
        }
        list = lists
        dataType = datas

        onMapLoaded(mapView.map)
    }

    fun drawSimpleBitmap(): Bitmap? {
        val bitmap = Bitmap.createBitmap(24, 24, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.color = Color.parseColor("#E39D32")
        paint.style = Paint.Style.FILL
        canvas.drawCircle((24 / 2).toFloat(), (24 / 2).toFloat(), (24 / 2).toFloat(), paint)
        return bitmap
    }

    private fun onMapLoaded(loadedMap: Map) {
        map = loadedMap
        var img: ImageProvider
        mapObject = map.mapObjects
        if (list?.isNotEmpty() == true && dataType?.isNotEmpty() == true) {
            for (i in list.indices) {
                for (data in dataType) {
                    val placemarkMapObject: PlacemarkMapObject

                    if(data.type.equals("10") || data.type.equals("11")) {
                         img =  ImageProvider.fromResource(context, R.drawable.service)
                        mapObject!!.addPlacemark(
                            Point(list[i].latitude, list[i].longitude), img)
                    } else if (data.type.equals("0") || data.type.equals("1")||data.type.equals("2")){
                         img =  ImageProvider.fromResource(context, R.drawable.animals)
                        mapObject!!.addPlacemark(
                            Point(list[i].latitude, list[i].longitude), img)
                    }

                }
            }
        } else Toast.makeText(context, "Активные объявления отсутствуют!", Toast.LENGTH_SHORT)
            .show()
    }

    override fun errorMessage(err: String?) {
        Toast.makeText(context, err, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        runnable?.let { handler.removeCallbacks(it) }
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        runnable?.let { handler.removeCallbacks(it) }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
        handler.postDelayed(Runnable {
            runnable?.let { handler.postDelayed(it, 5000) }
            presenter = GetInfoAdsPresenterKt(this)
            presenter?.getAdsInfo()
            getPosition()
        }.also { runnable = it }, 5000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        runnable?.let { handler.removeCallbacks(it) }
    }
}