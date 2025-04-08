package com.example.ebisconstore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ebisconstore.auth.LoginViewModel
import com.example.ebisconstore.category.ProductsViewModel

@Composable
fun AppMain(){
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel()
    val productViewModel: ProductsViewModel = hiltViewModel()
    val loginState by loginViewModel.loginState
    Scaffold(
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Navigation(
                viewModel = productViewModel,
                loginViewModel = loginViewModel
            )
        }
    }
}