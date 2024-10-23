package com.example.newanimals.viewModel.board

import android.content.Intent
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.newanimals.R
import com.example.newanimals.activity.AdsFixActivity
import com.example.newanimals.db.AdsData
import com.example.newanimals.db.AdsDataKt
import com.example.newanimals.utils.ReadRXFirebaseUtil
import com.google.accompanist.glide.rememberGlidePainter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class AdsDogViewModel: ViewModel() {
    private val databse = FirebaseFirestore.getInstance()
    private val itemsRef = databse.collection("AdsData")

    var adsData by mutableStateOf<List<AdsData>>(emptyList())
    var kindHands by mutableStateOf<List<AdsData>>(emptyList())
    var poteryashki by mutableStateOf<List<AdsData>>(emptyList())
    var findHome by mutableStateOf<List<AdsData>>(emptyList())

    init {
        fetchBook()
    }

    private fun fetchBook() {

        val findHomes: MutableList<AdsData> = ArrayList()
        val poteryashkis: MutableList<AdsData> = ArrayList()
        val kindHandss: MutableList<AdsData> = ArrayList()

        ReadRXFirebaseUtil.observeValueEvent(itemsRef)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<QuerySnapshot> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(documentSnapshots: QuerySnapshot) {
                    val data: MutableList<AdsData> = ArrayList()

                    for (document in documentSnapshots.documents) {
                        val adsDataKt: AdsData? =
                            document.toObject(AdsData::class.java)
                        if (adsDataKt != null) data.add(adsDataKt)
                    }

                    adsData = data
                    for (i in adsData.indices) {
                        if (adsData[i].typeAnimals == "1"){
                            when (adsData[i].type) {
                                "0" -> {
                                    findHomes.add(adsData[i])
                                }
                                "1" -> {
                                    kindHandss.add(adsData[i])
                                }
                                "2" -> {
                                    poteryashkis.add(adsData[i])
                                }
                            }
                        }
                    }
                    findHome = findHomes
                    kindHands = kindHandss
                    poteryashki = poteryashkis
                }

                override fun onError(e: Throwable) {
                    RxJavaPlugins.onError(e);
                }

                override fun onComplete() {}
            });
    }

    @Composable
    fun serviceView() {
        var back = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher;
        var currentList by remember { mutableStateOf(findHome) }
        currentList = findHome
        Column(
            Modifier
                .fillMaxSize()
                .background(colorResource(R.color.grey)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            Row(Modifier.fillMaxWidth()) {
                IconButton(onClick = {back?.onBackPressed() }) {
                    Image(imageVector = Icons.Default.ArrowBack, contentDescription = "arrow back",
                        Modifier.padding(top = 14.dp))

                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Button(onClick = { currentList = findHome},shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = colorResource(R.color.orange)),
                        border = BorderStroke(1.dp, colorResource(R.color.orange)),
                        modifier = Modifier
                            .background(colorResource(R.color.grey))
                            .padding(top = 10.dp, end = 10.dp, bottom = 10.dp)
                    ) {
                        Text("ИЩЕМ ДОМ", color = colorResource(R.color.orange))
                    }

                    Button(onClick = { currentList = kindHands},shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = colorResource(R.color.orange)),
                        border = BorderStroke(1.dp, colorResource(R.color.orange)),
                        modifier = Modifier
                            .background(colorResource(R.color.grey))
                            .padding(10.dp)
                    ) {
                        Text("ДОБРЫЕ РУКИ", color = colorResource(R.color.orange))
                    }

                }

            }
            lazyRecyclerView(data = currentList)
        }
    }

    @Composable
    private fun lazyRecyclerView(data: List<AdsData>) {
        val activity = LocalContext.current
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = rememberLazyGridState(),
            contentPadding = PaddingValues(2.dp),
            modifier = Modifier.background(colorResource(R.color.grey))
        ) {
            items(data.size) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .background(colorResource(R.color.grey))
                        .clickable {
                            val intent = Intent(activity, AdsFixActivity::class.java)
                            intent.putExtra("animal_name", data[it].name_animals)
                            intent.putExtra("age", data[it].age)
                            intent.putExtra("name_user", data[it].name_people +" " +data[it].surname_people.substring(0,1) + ".")
                            intent.putExtra("description", data[it].description)
                            intent.putExtra("date_lose", data[it].date_lose)
                            intent.putExtra("place_lose", data[it].address)
                            intent.putExtra("color", data[it].color)
                            intent.putExtra("poroda", data[it].poroda)
                            intent.putExtra("pol", data[it].pol)
                            intent.putExtra("imgUser", data[it].imgUser)
                            intent.putExtra("phone", data[it].phone)
                            intent.putExtra("imgAnimals", data[it].imgUrl)
                            activity.startActivity(intent)
                        },
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .height(110.dp)
                                .padding(
                                    start = 8.dp,
                                    top = 8.dp,
                                    end = 8.dp,
                                    bottom = 3.dp
                                )
                                .clip(RoundedCornerShape(15.dp)
                                )) {
                            Image(
                                painter = rememberGlidePainter(data[it].imgUrl),
                                contentDescription = "this image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Box {
                            Column(
                                modifier = Modifier.padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    bottom = 8.dp
                                )
                            ) {
                                Row {
                                    Text(
                                        data[it].name_animals,
                                        fontSize = 23.sp,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                    Image(
                                        painterResource(R.drawable.pol_girl),
                                        contentDescription = "this pol",
                                        modifier = Modifier.padding(start = 7.dp, top = 10.dp)
                                    )
                                }
                                Row(Modifier.padding(start = 8.dp)) {
                                    Image(
                                        painterResource(R.drawable.location),
                                        contentDescription = "this pol",
                                        modifier = Modifier
                                            .padding(end = 5.dp, top = 4.dp)
                                            .height(14.dp)
                                            .width(13.dp),
                                        alignment = Alignment.Center
                                    )
                                    Text(data[it].address, fontSize = 12.sp,
                                        modifier = Modifier
                                            .padding(end = 4.dp))

                                }

                                Row(Modifier.padding(start = 8.dp)) {
                                    Image(
                                        painterResource(R.drawable.date_image),
                                        contentDescription = "this pol",
                                        modifier = Modifier
                                            .padding(end = 5.dp, top = 4.dp)
                                            .height(13.dp)
                                            .width(14.dp),
                                        alignment = Alignment.Center

                                    )
                                    Text(data[it].date_lose, fontSize = 12.sp, textAlign = TextAlign.Center)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    @Preview
    fun servicePreviewView() {
        serviceView()
    }
}