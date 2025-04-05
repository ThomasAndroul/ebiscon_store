package com.example.ebisconstore

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ebisconstore.auth.LoginScreen
import com.example.ebisconstore.auth.LoginViewModel
import com.example.ebisconstore.category.CategoryScreen

@Composable
fun Navigation(
    loginState: LoginViewModel.LoginState,
    navController: NavController,
    loginViewModel: LoginViewModel
){

    val context = LocalContext.current

    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.LoginScreen.route) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                loginState = loginState,
                onLogin = { username, password ->
                    if (loginViewModel.login(username, password)) {
                        navController.navigate(Screen.CategoryScreen.route)
                    } else {
                        Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
        composable(Screen.CategoryScreen.route) { CategoryScreen() }
    }
}