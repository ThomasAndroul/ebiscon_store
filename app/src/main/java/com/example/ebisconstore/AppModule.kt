package com.example.ebisconstore

import com.example.ebisconstore.category.ProductApiService
import com.example.ebisconstore.category.ProductRepository
import com.example.ebisconstore.category.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ProductApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ProductApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductsRepository(
        productApiService: ProductApiService
    ): ProductRepository {
        return ProductRepositoryImpl(productApiService)
    }
}