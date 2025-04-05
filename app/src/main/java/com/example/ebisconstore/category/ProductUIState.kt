package com.example.ebisconstore.category

data class ProductUIState(
    val loading: Boolean = true,
    val error: String? = null,
    val products: List<Product> = emptyList()
)

sealed class ProductIntent{
    object LoadProducts: ProductIntent()
}
