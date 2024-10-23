package com.example.newanimals.utils.nav_bar

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.newanimals.R

@Composable
fun getBottomNavItems(): List<NavItem> {
    return listOf(
        NavItem(
            "Объявления",
             painterResource(id = R.drawable.ads_full),
             "ads"
        ),
        NavItem(
            "Карта",
            painterResource(id = R.drawable.map),
            "map"
        ),
        NavItem(
           "",
             painterResource(id = R.drawable.add),
           "add"
        ),
        NavItem(
            "Сообщения",
            painterResource(id = R.drawable.hands),
            "message"
        ),
        NavItem(
            "Профиль",
            painterResource(id = R.drawable.user),
            "user"
        )
    )
}