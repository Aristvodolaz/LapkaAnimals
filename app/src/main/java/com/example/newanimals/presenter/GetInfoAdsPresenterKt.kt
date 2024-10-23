package com.example.newanimals.presenter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.newanimals.db.AdsData
import com.example.newanimals.db.AdsDataKt
import com.example.newanimals.utils.ReadRXFirebaseUtil
import com.example.newanimals.view.GetDataView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class GetInfoAdsPresenterKt {
    private var getDataView: GetDataView? = null

    constructor(getDataView: GetDataView?) {
        this.getDataView = getDataView
    }
    var ads by  mutableStateOf<List<AdsData>>(emptyList())
    private val database = FirebaseDatabase.getInstance("https://lapka2-5144b-default-rtdb.europe-west1.firebasedatabase.app")
    private val userLoginRef = database.reference.child("AdsData")

    fun getAdsInfo() {
        userLoginRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val data: MutableList<AdsData> = ArrayList()
                    for (dataSnapshot in snapshot.children) {
                        val adsDataKt: AdsData? = dataSnapshot.getValue(AdsData::class.java)
                        if (adsDataKt != null) data.add(adsDataKt)
                    }
                    ads = data
                    getDataView?.getInfoAds(ads)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                getDataView?.errorMessage(error.message)
            }
        })
    }
}