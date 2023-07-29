package com.example.newanimals.presenter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.newanimals.db.AdsDataKt
import com.example.newanimals.utils.ReadRXFirebaseUtil
import com.example.newanimals.view.GetDataView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class GetInfoAdsPresenterKt {
    private var getDataView: GetDataView? = null
    var ads: List<AdsDataKt> = ArrayList()

    constructor(getDataView: GetDataView?) {
        this.getDataView = getDataView
    }
    var adsData by mutableStateOf<List<AdsDataKt>>(emptyList())

    private val databse = FirebaseFirestore.getInstance()
    private val itemsRef = databse.collection("AdsData")
    fun getAdsInfo() {
        ReadRXFirebaseUtil.observeValueEvent(itemsRef)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<QuerySnapshot> {
                override fun onSubscribe(d: Disposable) {}

                override fun onNext(documentSnapshots: QuerySnapshot) {
                    val data: MutableList<AdsDataKt> = ArrayList()
                    for (document in documentSnapshots.documents) {
                        val type = document.getString("type")
                        val name_type = document.getString("name_type")
                        val name_animals = document.getString("name_animals")
                        val description = document.getString("description")
                        val name_people = document.getString("name_people")
                        val surname_people = document.getString("surname_people")
                        val service = document.getString("service")
                        val address = document.getString("address")
                        val date_lose = document.getString("date_lose")
                        val days = document.getString("days")
                        val price = document.getString("price")
                        val lat = document.getString("lat")
                        val lon = document.getString("lon")
                        val typeAnimals = document.getString("typeAnimals")
                        val imgUrl = document.getString("imgUrl")
                        val age = document.getString("age")
                        val poroda = document.getString("poroda")
                        val color = document.getString("color")
                        val pol = document.getString("pol")
                        val imgUser = document.getString("imgUser")
                        var phone = document.getString("phone")
                        if (type != null && name_type != null && name_animals != null && description != null && name_people != null &&
                            surname_people != null && service != null && address != null && date_lose != null &&
                            days != null && price != null && lat != null && lon != null && typeAnimals != null && imgUrl != null
                            && age != null && poroda != null && color != null && pol != null && imgUser != null && phone != null
                        ) {
                            data.add(
                                AdsDataKt(
                                    type,
                                    name_type,
                                    name_animals,
                                    description,
                                    name_people,
                                    surname_people,
                                    service,
                                    address,
                                    date_lose,
                                    days,
                                    price,
                                    lat.toFloat(),
                                    lon.toFloat(),
                                    typeAnimals,
                                    imgUrl,
                                    age, poroda, color, pol, imgUser, phone
                                )
                            )
                        }

                    }
                    adsData = data
                    getDataView?.getInfoAds(adsData)
                }

                override fun onError(e: Throwable) {
                    RxJavaPlugins.onError(e);
                }

                override fun onComplete() {}
            });

    }
}