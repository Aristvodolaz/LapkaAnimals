package com.example.newanimals.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.newanimals.R
import com.google.accompanist.glide.rememberGlidePainter

class AdsServiceFixActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ads = intent.getStringExtra("ads")
        setContent {
//            if (ads != null) {
            adsView()
//            }
        }
    }
    @Composable
    @Preview
    fun adsView() {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = rememberGlidePainter(intent.getStringExtra("imgAnimals")),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                        .height(356.dp)
                )

                Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .zIndex(1f) // Поднимаем Card наверх
                    .background(color = Color.Transparent) // Прозрачный фон
                    .align(Alignment.BottomStart), // Выравниваем Card внизу слева
                shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
                elevation = 8.dp
            ){

                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp, start = 16.dp)) {
                    Row(Modifier.fillMaxWidth()) {
                        intent.getStringExtra("service")?.let { Text(it, fontSize = 32.sp) }

                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)) {
                        Image(
                            painterResource(R.drawable.money), contentDescription ="date",
                            Modifier
                                .padding(end = 8.dp)
                                .height(28.dp)
                                .width(28.dp))
                        intent.getStringExtra("price")?.let { Text(it, fontSize = 17.sp) }
                    }


                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)) {
                        if(intent.getStringExtra("imgUser").equals("")){
                            Image(
                                rememberGlidePainter(resources.getDrawable(R.drawable.logo)),
                                contentDescription = "name",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(64.dp)
                                    .width(64.dp)
                                    .clip(RoundedCornerShape(50.dp))
                            )

                        } else {
                            Image(
                                rememberGlidePainter(intent.getStringExtra("imgUser")),
                                contentDescription = "name",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(64.dp)
                                    .width(64.dp)
                                    .clip(RoundedCornerShape(50.dp))
                            )
                        }
                        Column(
                            Modifier.padding(start = 12.dp, top = 12.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            intent.getStringExtra("name_user")?.let { Text(it, fontSize = 20.sp) }
                            intent.getStringExtra("phone")?.let { Text(it, fontSize = 14.sp) }
                        }
                    }
                    intent.getStringExtra("description")?.let {
                        Text(
                            it,
                            modifier = Modifier.padding(top = 8.dp))
                    }

                    Row(
                        Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                        Image(painter = painterResource(R.drawable.call), contentDescription = "call",
                            modifier = Modifier
                                .height(45.dp)
                                .width(45.dp)
                                .padding(start = 12.dp)
                        )
                        Image(painter = painterResource(R.drawable.sms), contentDescription = "sms",
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                                .padding(start = 18.dp)
                        )

                        Button(
                            onClick = {

                            },
                            modifier = Modifier
                                .padding(top = 16.dp, start = 50.dp, end = 16.dp)
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(Color(0xff47BFE5)),
                            shape = RoundedCornerShape(30.dp),
                        ) {
                            Text("ОБСУЖДЕНИЕ", modifier = Modifier.padding(2.dp),
                                style = MaterialTheme.typography.button.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
                }

                IconButton(onClick = { this@AdsServiceFixActivity.finish() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "arrow back",
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp)
                    )
                }
            }
        }
    }
}