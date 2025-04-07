package com.example.ebisconstore.category

class ProductRepositoryImpl(
    private val productApiService: ProductApiService
) : ProductRepository {
    override suspend fun getProducts(): List<Product> {
        return productApiService.getProducts()
    }
    override  suspend fun updateProduct(product: Product): Product {
        return productApiService.updateProduct(product.id, product)
    }
}