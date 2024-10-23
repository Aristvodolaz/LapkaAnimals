package com.example.newanimals.viewModel.board

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.newanimals.R
import com.example.newanimals.activity.AddAdsActivity
import com.example.newanimals.activity.WelcomeActivity
import com.example.newanimals.fragment.*
import com.example.newanimals.fragment.ads.AdsAnimalsFindHomeFragment
import com.example.newanimals.fragment.ads.AdsAnimalsKindHandsFragment
import com.example.newanimals.fragment.ads.AdsAnimalsPoteryashkiFragment
import com.example.newanimals.fragment.ads.AdsCatFragment
import com.example.newanimals.fragment.ads.AdsDogFragment
import com.example.newanimals.fragment.ads.AdsPerederzhkaFragment
import com.example.newanimals.fragment.ads.AdsServiceFragment

class AdsBoardViewModel: ViewModel() {
    @Composable
    fun viewCreateAds() {
        val context = LocalContext.current
        val activity = LocalContext.current as WelcomeActivity
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFD9D9D9))
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xffE39D32))
            ) {
                IconButton(onClick = { activity?.supportFragmentManager?.popBackStack() }) {
                    Image(imageVector = Icons.Default.ArrowBack, contentDescription = "arrow back")

                }
            }

            Card(
                shape = RoundedCornerShape(bottomStart = 1000.dp, bottomEnd = 1000.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(Color(0xFFD9D9D9))
                    .padding(bottom = 15.dp)
            ) {    Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(Color(0xffE39D32)),
                contentAlignment = Alignment.Center,
            ){
                Text( "ДОСКА ОБЪЯВЛЕНИЙ",
                    fontSize = 22.sp, textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp),
                    color = Color.White)
            }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xffD9D9D9)).padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceAround) {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(90.dp)
                        .weight(1f)
                        .clickable {
                            activity.replaceFragment(
                                AdsDogFragment.newInstance(),
                                true
                            )
                        },
                    shape = RoundedCornerShape(7.dp)
                ) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xffE39D32))) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically) {
                            Text("СОБАКИ",
                                Modifier.padding(start = 12.dp),
                                fontSize = 18.sp,
                                color = Color.White,
                                fontFamily = MaterialTheme.typography.caption.fontFamily)

                            Image(painter = painterResource(R.drawable.dog), contentDescription = "dog",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 16.dp))
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(90.dp)
                        .weight(1f)
                        .clickable {
                            activity.replaceFragment(
                                AdsCatFragment.newInstance(),
                                true
                            )
                        },
                    shape = RoundedCornerShape(7.dp)
                ){
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xff47BFE5))) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically) {
                            Text("КОШКИ",
                                Modifier.padding(start = 12.dp),
                                fontSize = 18.sp,
                                color = Color.White,
                                fontFamily = MaterialTheme.typography.caption.fontFamily)

                            Image(painter = painterResource(R.drawable.cats), contentDescription = "cat",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 16.dp))
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xffD9D9D9)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Card(
                        modifier = Modifier
                            .background(Color(0xffD9D9D9))
                            .padding(8.dp)
                            .height(88.dp)
                            .weight(1f)
                            .clickable {
                                activity.replaceFragment(
                                    AdsAnimalsKindHandsFragment.newInstance(),
                                    true
                                )
                            },
                        shape = RoundedCornerShape(7.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Image(
                                painter = painterResource(R.drawable.__icon__donate_),
                                contentDescription = "free",
                                modifier = Modifier
                                    .padding(top = 2.dp, bottom = 10.dp)
                                    .width(56.dp)
                                    .height(41.dp),
                            )
                            Text(
                                "добрые руки",
                                fontSize = 12.sp,
                                color = Color(0xffE39D32)
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .background(Color(0xffD9D9D9))
                            .padding(8.dp)
                            .height(88.dp)
                            .weight(1f)
                            .clickable {
                                activity.replaceFragment(
                                    AdsAnimalsPoteryashkiFragment.newInstance(),
                                    true
                                )
                            },
                        shape = RoundedCornerShape(7.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(R.drawable.__icon__doc_search_),
                                contentDescription = "free",
                                modifier = Modifier
                                    .padding(top = 2.dp)
                                    .width(56.dp)
                                    .height(57.dp)
                            )
                            Text(
                                "потеряшки",
                                fontSize = 12.sp,
                                color = Color(0xffE39D32)
                            )
                        }

                    }
                    Card(
                        modifier = Modifier
                            .background(Color(0xffD9D9D9))
                            .padding(8.dp)
                            .height(88.dp)
                            .weight(1f)
                            .clickable {
                                activity.replaceFragment(
                                    AdsAnimalsFindHomeFragment.newInstance(),
                                    true
                                )
                            },
                        shape = RoundedCornerShape(7.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(R.drawable.__icon__home_),
                                contentDescription = "free",
                                modifier = Modifier
                                    .padding(top = 2.dp)
                                    .width(56.dp)
                                    .height(56.dp)
                            )
                            Text(
                                "мы ищем дом",
                                fontSize = 12.sp,
                                color = Color(0xffE39D32)
                            )
                        }

                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Card(
                        modifier = Modifier
                            .background(Color(0xffD9D9D9))
                            .height(88.dp)
                            .weight(1f)
                            .padding(2.dp)
                            .padding(start = 6.dp, top = 2.dp, bottom = 2.dp)
                            .clickable {
                                activity.replaceFragment(
                                    AdsServiceFragment.newInstance(),
                                    true
                                )
                            },
                        shape = RoundedCornerShape(7.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(2.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(R.drawable.__icon__newspaper_folding_),
                                contentDescription = "free",
                                modifier = Modifier
                                    .width(56.dp)
                                    .height(56.dp)
                            )
                            Text(
                                "услуги",
                                fontSize = 12.sp,
                                color = Color(0xff47BFE5),
//                            modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .background(Color(0xffD9D9D9))
                            .height(88.dp)
                            .weight(1f)
                            .padding(4.dp)
                            .padding(end = 4.dp)
                            .clickable {
                                activity.replaceFragment(
                                    AdsPerederzhkaFragment.newInstance(),
                                    true
                                )
                            },
                        shape = RoundedCornerShape(7.dp)
                    ) {
                        Column(
//                        modifier = Modifier.padding(5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(R.drawable.perederzhka),
                                contentDescription = "free",
                                modifier = Modifier
                                    .padding(top = 2.dp)
                                    .width(56.dp)
                                    .height(56.dp)
                            )
                            Text(
                                "передержка",
                                fontSize = 12.sp,
                                color = Color(0xff47BFE5),
                            )
                        }
                    }
                }
            }
        }
    }
    @Composable
    @Preview
    fun viewPreviewCreateAds(){
        viewCreateAds()
    }
}