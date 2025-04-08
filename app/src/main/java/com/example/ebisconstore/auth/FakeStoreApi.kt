package com.example.ebisconstore.auth

import retrofit2.http.Body
import retrofit2.http.POST

interface FakeStoreApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}