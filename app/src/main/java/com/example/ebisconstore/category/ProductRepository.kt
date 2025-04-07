package com.example.ebisconstore.category

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun updateProduct(product: Product): Product
}