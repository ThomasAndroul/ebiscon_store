package com.example.ebisconstore

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ebisconstore.auth.LoginScreen
import com.example.ebisconstore.auth.LoginViewModel
import com.example.ebisconstore.category.CategoryScreen
import com.example.ebisconstore.category.ProductScreen
import com.example.ebisconstore.category.ProductsViewModel

@Composable
fun Navigation(
    viewModel: ProductsViewModel = hiltViewModel(),
    loginState: LoginViewModel.LoginState,
    navController: NavController,
    loginViewModel: LoginViewModel
){

    val context = LocalContext.current
    val uiState by viewModel.uiState

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
        composable(Screen.CategoryScreen.route) {
            CategoryScreen(
                uiState = uiState,
                navigateToCategory = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("category", it)
                    navController.navigate(Screen.ProductScreen.route)
                }
            )
        }
        composable(Screen.ProductScreen.route) {
            val category = navController.previousBackStackEntry?.savedStateHandle?.get<String>("category") ?: ""
            ProductScreen(
                uiState = uiState,
                category = category
            )
        }
    }
}