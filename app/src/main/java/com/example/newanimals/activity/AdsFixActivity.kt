package com.example.newanimals.activity

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.example.newanimals.R
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.accompanist.glide.rememberGlidePainter

class AdsFixActivity: ComponentActivity() {

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
        Column(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()) {
            Row(modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth()
                .background(color = Color.Blue)
            ) {
                Box {
                    Image(
                        rememberGlidePainter(intent.getStringExtra("imgAnimals")),
                        contentDescription = "image",
                        contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize()
                    )
                    IconButton(onClick = {this@AdsFixActivity.finish() }) {
                        Image(imageVector = Icons.Default.ArrowBack, contentDescription = "arrow back",
                        modifier = Modifier.align(Alignment.TopStart)
                            .padding(12.dp))

                    }
                }
            }

            Card(modifier = Modifier
                .fillMaxHeight(2f)
                .fillMaxWidth()
                .background(colorResource(R.color.grey)),
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
            ){
                
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp, start = 16.dp)) {
                    Row(Modifier.fillMaxWidth()) {
                        intent.getStringExtra("animal_name")?.let { Text(it, fontSize = 32.sp) }

                        if (intent.getStringExtra("pol") == "0") {
                            Image(
                                painterResource(R.drawable.pol_girl), contentDescription = "pol",
                                Modifier
                                    .padding(
                                        start = 8.dp,
                                        top = 12.dp
                                    )
                                    .height(25.dp)
                                    .width(17.dp)
                            )
                        }
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)) {
                        Image(painterResource(R.drawable.location), contentDescription ="location",
                            Modifier
                                .padding(end = 8.dp)
                                .height(24.dp)
                                .width(19.dp))
                        intent.getStringExtra("place_lose")?.let { Text(it, fontSize = 17.sp) }
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)) {
                        Image(painterResource(R.drawable.date_image), contentDescription ="date",
                            Modifier
                                .padding(end = 8.dp)
                                .height(20.dp)
                                .width(18.dp))
                        intent.getStringExtra("date_lose")?.let { Text(it, fontSize = 17.sp) }
                    }
                    
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp, top = 12.dp) ) {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .height(70.dp)
                            .padding(8.dp)
                            .border(
                                1.dp, colorResource(
                                    R.color.orange
                                ),
                                RoundedCornerShape(15.dp)
                            )){
                            Column(modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Возраст")
                                intent.getStringExtra("age")?.let { Text(it) }
                            }
                        }
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .weight(1f)
                            .padding(8.dp)
                            .border(
                                1.dp, colorResource(
                                    R.color.orange,
                                ),
                                RoundedCornerShape(15.dp)
                            )
                        ){
                            Column(modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Порода")
                                intent.getStringExtra("poroda")
                                    ?.let { Text(it, textAlign = TextAlign.End) }
                            }
                        }
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .height(70.dp)
                            .padding(8.dp)
                            .border(
                                1.dp, colorResource(
                                    R.color.orange
                                ),
                                RoundedCornerShape(15.dp)
                            )){
                            Column(modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Окрас")
                                intent.getStringExtra("color")?.let { Text(it) }
                            }

                        }
                    }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)) {
                        Image(   rememberGlidePainter(intent.getStringExtra("imgUser")), contentDescription = "name",
                            contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(64.dp)
                            .width(64.dp)
                            .clip(RoundedCornerShape(50.dp)))

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
                                ))
                        }
                    }
                }

            }
        }

    }
}