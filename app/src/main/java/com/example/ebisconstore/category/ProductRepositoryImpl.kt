package com.example.ebisconstore.category

class ProductRepositoryImpl(
    private val productApiService: ProductApiService
) : ProductRepository {
    override suspend fun getProducts(): List<Product> {
        return productApiService.getProducts()
    }
}