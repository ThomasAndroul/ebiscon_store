package com.example.ebisconstore.category

import retrofit2.http.GET

interface ProductApiService {
    @GET("/products")
    suspend fun getProducts(): List<Product>
}