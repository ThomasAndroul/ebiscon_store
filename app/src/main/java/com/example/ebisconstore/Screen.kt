package com.example.ebisconstore

sealed class Screen(
    val route: String
) {
    data object LoginScreen : Screen("login_screen")
    data object CategoryScreen : Screen("category_screen")
    data object ProductScreen : Screen("product_screen")
    data object ProductDetailScreen : Screen("product_detail_screen")
}