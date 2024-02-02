package com.example.myonlinemarket.navigation

import com.example.myonlinemarket.R

sealed class Destinations(
    val route: String,
    val title: String? = null,
    val icon: Int? = null
) {
    object HomeScreen : Destinations(
        route = "home_screen",
        title = "Главная",
        icon = R.drawable.home
    )

    object BasketScreen : Destinations(
        route = "basket_screen",
        title = "Корзина",
        icon = R.drawable.basket
    )

    object CatalogScreen : Destinations(
        route = "catalog_screen",
        title = "Каталог",
        icon = R.drawable.catalog
    )

    object SaleScreen : Destinations(
        route = "sale_screen",
        title = "Акции",
        icon = R.drawable.sale
    )

    object ProfileScreen : Destinations(
        route = "profile_screen",
        title = "Профиль",
        icon = R.drawable.profile
    )

}

