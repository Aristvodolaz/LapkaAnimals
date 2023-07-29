package com.example.newanimals.view

import com.example.newanimals.db.AdsDataKt

interface GetDataView {
    fun getInfoAds(data: List<AdsDataKt>?)
    fun errorMessage(err: String?)
}