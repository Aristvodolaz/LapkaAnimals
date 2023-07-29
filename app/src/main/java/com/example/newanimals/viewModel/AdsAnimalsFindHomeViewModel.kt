package com.example.newanimals.viewModel

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
import coil.compose.rememberImagePainter
import com.example.newanimals.R
import com.example.newanimals.activity.AdsFixActivity
import com.example.newanimals.db.AdsDataKt
import com.example.newanimals.utils.ReadRXFirebaseUtil
import com.google.accompanist.glide.rememberGlidePainter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.auth.User
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable


class AdsAnimalsFindHomeViewModel: ViewModel() {

    private val databse = FirebaseFirestore.getInstance()
    private val itemsRef = databse.collection("AdsData")

    var adsData by mutableStateOf<List<AdsDataKt>>(emptyList())
    var cats by mutableStateOf<List<AdsDataKt>>(emptyList())
    var dogs by mutableStateOf<List<AdsDataKt>>(emptyList())
    var others by mutableStateOf<List<AdsDataKt>>(emptyList())

    init{ fetchBook() }

    private fun fetchBook() {

        val cat: MutableList<AdsDataKt> = ArrayList()
        val dog: MutableList<AdsDataKt> = ArrayList()
        val other: MutableList<AdsDataKt> = ArrayList()

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
                        if(type!=null && name_type!=null && name_animals!=null && description!=null && name_people!=null &&
                                surname_people!=null && service !=null && address!=null && date_lose!=null &&
                                days !=null && price!=null && lat !=null && lon !=null && typeAnimals!=null && imgUrl!=null
                            && age!=null && poroda!=null && color!=null && pol!=null && imgUser !=null && phone!=null) {
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
                                    age,poroda, color,pol, imgUser,phone
                                )
                            )
                        }

                }

                    adsData = data
                    for(i in adsData.indices) {
                        if (adsData[i].type == "0"){
                            when (adsData[i].typeAnimals) {
                                "0" -> {
                                    cat.add(adsData[i])
                                }
                                "1" -> {
                                    dog.add(adsData[i])
                                }
                                else -> other.add(adsData[i])
                            }
                    }
                    }
                    dogs = dog
                    cats = cat
                    others = other
                    

                }
                override fun onError(e: Throwable) {
                    RxJavaPlugins.onError(e);
                }
                override fun onComplete() {}
            });

            }


    @Composable
    fun animalsView(){
        var back = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher;
        var currentList by remember { mutableStateOf(cats) }
        currentList = cats
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
                    Button(onClick = { currentList = cats},shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = colorResource(R.color.orange)),
                        border = BorderStroke(1.dp, colorResource(R.color.orange)),
                        modifier = Modifier
                            .background(colorResource(R.color.grey))
                            .padding(top = 10.dp, end = 10.dp, bottom = 10.dp)
                    ) {
                        Text("Кошки", color = colorResource(R.color.orange))
                    }
                    Button(onClick = { currentList = dogs },shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = colorResource(R.color.orange)),
                        border = BorderStroke(1.dp, colorResource(R.color.orange)),
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Text("Собаки", color = colorResource(R.color.orange))
                    }

                    Button(onClick = { currentList = others},shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = colorResource(R.color.orange)),
                        border = BorderStroke(1.dp, colorResource(R.color.orange)),
                        modifier = Modifier
                            .background(colorResource(R.color.grey))
                            .padding(10.dp)
                    ) {
                        Text("Другие", color = colorResource(R.color.orange))
                    }

                }

            }
            lazyRecyclerView(data = currentList)
        }
    }

    @Composable
    private fun lazyRecyclerView(data: List<AdsDataKt>) {
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
                                Row {
                                    Text(
                                        data[it].name_animals,
                                        fontSize = 23.sp,
                                        modifier = Modifier.padding(start = 4.dp)
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
                                            .height(12.dp)
                                            .width(11.dp)
                                    )
                                    Text(data[it].address, fontSize = 12.sp)
                                }

                                Row(Modifier.padding(start = 8.dp)) {
                                    Image(
                                        painterResource(R.drawable.date_image),
                                        contentDescription = "this pol",
                                        modifier = Modifier
                                            .padding(end = 5.dp, top = 4.dp)
                                            .height(11.dp)
                                            .width(12.dp)
                                    )
                                    Text(data[it].date_lose, fontSize = 12.sp)
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
    fun animalsPreviewView(){
        animalsView()
    }
}