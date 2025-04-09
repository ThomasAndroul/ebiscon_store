package com.example.ebisconstore.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userManager: UserManager,
    private val apiService: FakeStoreApi
) : ViewModel() {

    data class LoginState(
        val loading: Boolean = false,
        val token: String? = null,
        val error: String? = null
    )

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState
    val TAG = "LoginViewModel"


    init {
        viewModelScope.launch {
            userManager.tokenFlow.collect { token ->
                if (!token.isNullOrEmpty()) {
                    _loginState.value = LoginState(loading = false, token = token)
                } else {
                    _loginState.value = LoginState(loading = false)
                }
            }
        }
    }

    fun login(username: String, password: String, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            _loginState.value = LoginState(loading = true)
            try {
                val response = apiService.login(LoginRequest(username, password))
                val token = response.token
                Log.d(TAG, "Token: $token")

                userManager.saveToken(token)
                _loginState.value = LoginState(loading = false, token = token)
                onSuccess()
            } catch (e: Exception) {
                _loginState.value = LoginState(loading = false, error = e.message)
                onError()
            }
        }
    }
    fun clearUserToken() {
        viewModelScope.launch {
            userManager.clearToken()
            _loginState.value = LoginState()
        }
    }
}
