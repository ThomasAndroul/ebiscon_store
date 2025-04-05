package com.example.ebisconstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ebisconstore.auth.FakeStoreApi
import com.example.ebisconstore.auth.LoginViewModel
import com.example.ebisconstore.auth.UserRepository
//import com.example.ebisconstore.auth.api
import com.example.ebisconstore.ui.theme.EbisconStoreTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EbisconStoreTheme {
                AppMain()
            }
        }
    }
}