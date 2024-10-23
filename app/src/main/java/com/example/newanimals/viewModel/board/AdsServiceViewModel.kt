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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.newanimals.R
import com.example.newanimals.activity.AdsFixActivity
import com.example.newanimals.activity.AdsServiceFixActivity
import com.example.newanimals.db.AdsData
import com.example.newanimals.db.AdsDataKt
import com.example.newanimals.utils.ReadRXFirebaseUtil
import com.google.accompanist.glide.rememberGlidePainter
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

class AdsServiceViewModel: ViewModel() {

    private val database = FirebaseDatabase.getInstance("https://lapka2-5144b-default-rtdb.europe-west1.firebasedatabase.app")
    private val userLoginRef = database.reference.child("AdsData")

    var adsData by mutableStateOf<List<AdsData>>(emptyList())
    var service by mutableStateOf<List<AdsData>>(emptyList())
    var needService by mutableStateOf<List<AdsData>>(emptyList())
    val errorMessage = mutableStateOf<String?>(null)

    init{ fetchBook() }

    private fun fetchBook() {
        userLoginRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val data: MutableList<AdsData> = ArrayList()
                    for (dataSnapshot in snapshot.children) {
                        val adsDataKt: AdsData? = dataSnapshot.getValue(AdsData::class.java)
                        if (adsDataKt != null) data.add(adsDataKt)
                    }
                    adsData = data

                    val services: MutableList<AdsData> = ArrayList()
                    val needServices: MutableList<AdsData> = ArrayList()

                    adsData = data
                    for (i in adsData.indices) {
                        when (adsData[i].type) {
                            "10" -> {
                                services.add(adsData[i])
                            }

                            "11" -> {
                                needServices.add(adsData[i])
                            }
                        }
                    }
                    service = services
                    needService = needServices
                }
            }
                override fun onCancelled(error: DatabaseError) {
                    errorMessage.value = error.message
                }
            })
    }

    @Composable
    fun serviceView(){
        var back = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher;
        var currentList by remember { mutableStateOf(service) }
        currentList = service
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
                    Button(onClick = { currentList = service},shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = colorResource(R.color.orange)),
                        border = BorderStroke(1.dp, colorResource(R.color.orange)),
                        modifier = Modifier
                            .background(colorResource(R.color.grey))
                            .padding(top = 10.dp, end = 10.dp, bottom = 10.dp)
                    ) {
                        Text("УСЛУГИ", color = colorResource(R.color.orange))
                    }
                    Button(onClick = { currentList = needService },shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = colorResource(R.color.orange)),
                        border = BorderStroke(1.dp, colorResource(R.color.orange)),
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Text("ИЩЕМ УСЛУГУ", color = colorResource(R.color.orange))
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
                            val intent = Intent(activity, AdsServiceFixActivity::class.java)
                            intent.putExtra("price", data[it].price)
                            intent.putExtra(
                                "name_user",
                                data[it].name_people + " " + data[it].surname_people.substring(
                                    0,
                                    1
                                ) + "."
                            )
                            intent.putExtra("description", data[it].description)
                            intent.putExtra("place_lose", data[it].address)
                            intent.putExtra("pol", data[it].pol)
                            intent.putExtra("service", data[it].service)
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
                        Image(
                            painter = rememberGlidePainter(data[it].imgUrl),
                            contentDescription = "this image",
                            modifier = Modifier
                                .padding(
                                    start = 8.dp,
                                    top = 8.dp,
                                    end = 8.dp,
                                    bottom = 3.dp
                                )
                                .clip(RoundedCornerShape(15.dp))
                        )

                        Box {
                            Column(
                                modifier = Modifier.padding(
                                    start = 8.dp,
                                    top = 4.dp,
                                    bottom = 8.dp
                                )
                            ) {

                                Text(
                                    data[it].name_people +" "+ data[it].surname_people.substring(0,1)+".",
                                    fontSize = 23.sp,
                                    modifier = Modifier.padding(start = 4.dp)
                                )

                                Row(Modifier.padding(start = 8.dp)) {
                                    Image(
                                        painterResource(R.drawable.fre_hands_for_service),
                                        contentDescription = "this pol",
                                        modifier = Modifier
                                            .padding(end = 5.dp, top = 2.dp)
                                            .height(17.dp)
                                            .width(19.dp)
                                    )
                                    Text(data[it].service, fontSize = 12.sp)
                                }

                                Row(Modifier.padding(start = 8.dp)) {
                                    Image(
                                        painterResource(R.drawable.money),
                                        contentDescription = "this pol",
                                        modifier = Modifier
                                            .padding(end = 5.dp, top = 4.dp)
                                            .height(15.dp)
                                            .width(18.dp)
                                    )
                                    Text(data[it].price, fontSize = 12.sp)
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
    fun servicePreviewView(){
        serviceView()
    }
}