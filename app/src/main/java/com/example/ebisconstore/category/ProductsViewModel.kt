package com.example.ebisconstore.category

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {

    private val _uiState = mutableStateOf(ProductUIState())
    val uiState: State<ProductUIState> = _uiState

    init {
        handleIntent(ProductIntent.LoadProducts)
    }

    private fun handleIntent(intent: ProductIntent){
        when(intent){
            is ProductIntent.LoadProducts -> fetchProducts()
        }
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)
            try{
                val products = productRepository.getProducts()
                _uiState.value = ProductUIState(
                    loading = false,
                    products = products
                )
            }catch (e: Exception){
                _uiState.value = ProductUIState(
                    loading = false,
                    error = e.message
                )
            }
        }
    }
}