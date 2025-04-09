package com.example.ebisconstore

import com.example.ebisconstore.category.Product
import com.example.ebisconstore.category.ProductRepository
import com.example.ebisconstore.category.ProductsViewModel
import com.example.ebisconstore.category.Rating
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsViewModelTest {

    private lateinit var repository: ProductRepository
    private lateinit var viewModel: ProductsViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch products updates uiState with products`() = runTest {
        val mockProducts = listOf(
            Product(id = 1, title = "Product 1", price = 10.0, description = "", category = "", image = "", rating = Rating(0.0, 0)),
            Product(id = 2, title = "Product 2", price = 20.0, description = "", category = "", image = "", rating = Rating(0.0, 0))
        )

        whenever(repository.getProducts()).thenReturn(mockProducts)

        viewModel = ProductsViewModel(repository)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.loading)
        assertEquals(mockProducts, state.products)
        assertNull(state.error)
    }

    @Test
    fun `update product replaces it in the state when refetch is false`() = runTest {
        val originalProduct = Product(id = 1, title = "Old Product", price = 10.0, description = "", category = "", image = "", rating = Rating(0.0, 0))
        val updatedProduct = originalProduct.copy(title = "Updated Product")

        whenever(repository.getProducts()).thenReturn(listOf(originalProduct))
        whenever(repository.updateProduct(any())).thenReturn(updatedProduct)

        viewModel = ProductsViewModel(repository)
        advanceUntilIdle()

        var onSuccessCalled = false
        viewModel.updateProduct(updatedProduct, refetch = false, {
            onSuccessCalled = true
        }, {
            fail("onError should not be called")
        })

        advanceUntilIdle()

        val products = viewModel.uiState.value.products
        assertEquals(1, products.size)
        assertEquals("Updated Product", products.first().title)
        assertTrue(onSuccessCalled)
    }

    @Test
    fun `update product triggers refetch when refetch is true`() = runTest {
        val original = Product(id = 1, title = "Old", price = 0.0, description = "", category = "", image = "", rating = Rating(0.0, 0))
        val updated = Product(id = 1, title = "Updated", price = 0.0, description = "", category = "", image = "", rating = Rating(0.0, 0))
        val newList = listOf(updated)

        whenever(repository.getProducts()).thenReturn(listOf(original), newList)
        whenever(repository.updateProduct(any())).thenReturn(updated)

        viewModel = ProductsViewModel(repository)
        advanceUntilIdle()

        viewModel.updateProduct(updated, refetch = true, {}, {})
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals("Updated", state.products.first().title)
    }
}