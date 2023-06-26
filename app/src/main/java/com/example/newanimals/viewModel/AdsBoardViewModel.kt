package com.example.newanimals.viewModel

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.newanimals.R
import com.example.newanimals.activity.AddAdsActivity
import com.example.newanimals.activity.WelcomeActivity
import com.example.newanimals.fragment.AdsAnimalsFindHomeFragment

class AdsBoardViewModel: ViewModel() {
    @Composable
    fun viewCreateAds() {
        val context = LocalContext.current
        val activity = LocalContext.current as WelcomeActivity
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xffD9D9D9)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Card(                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xffD9D9D9))
                    .padding(24.dp).padding(bottom = 64.dp, top = 12.dp)

            ) {
                Text( "Выберите тип объявления\n для публикации",
                    fontSize = 22.sp, textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp),
                    color = Color(0xffE39D32)
                )
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
                            .height(84.dp)
                            .weight(1f)
                            .clickable {
                                val intent = Intent(context, AddAdsActivity::class.java)
                                intent.putExtra("type", "free")
                                context.startActivity(intent)
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
                            .height(84.dp)
                            .weight(1f)
                            .clickable {
                                val intent = Intent(context, AddAdsActivity::class.java)
                                intent.putExtra("type", "poterya")
                                context.startActivity(intent)
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
                            .height(84.dp)
                            .weight(1f)
                            .clickable {
                               activity.replaceFragment(AdsAnimalsFindHomeFragment.newInstance(), true)
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
                            .height(84.dp)
                            .weight(1f)
                            .padding(2.dp)
                            .padding(start = 6.dp, top = 2.dp, bottom = 2.dp)
                            .clickable {
                                val intent = Intent(context, AddAdsActivity::class.java)
                                intent.putExtra("type", "service")
                                context.startActivity(intent)
                            },
                        shape = RoundedCornerShape(7.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(3.dp),
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
                            .height(84.dp)
                            .weight(1f)
                            .padding(4.dp)
                            .padding(end = 4.dp) .clickable {
                                val intent = Intent(context, AddAdsActivity::class.java)
                                intent.putExtra("type", "perederzhka")
                                context.startActivity(intent)
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
//                            modifier = Modifier.padding(bottom = 8.dp)
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