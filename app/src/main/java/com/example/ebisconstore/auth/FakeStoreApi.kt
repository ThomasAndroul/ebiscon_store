package com.example.ebisconstore.auth

import retrofit2.Response
import retrofit2.http.GET

interface FakeStoreApi {
    @GET("users")
    suspend fun getUsers(): List<User>
}