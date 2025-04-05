package com.example.ebisconstore

sealed class Screen(
    val route: String
) {
    data object App : Screen("app")
    data object LoginScreen : Screen("login_screen")
    data object CategoryScreen : Screen("category_screen")
}