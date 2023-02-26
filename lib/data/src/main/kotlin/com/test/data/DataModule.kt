package com.test.data

import com.test.data.repository.ProductsRepository
import com.test.data.source.ProductsRemoteDataSource
import com.test.data.usecase.GetProductListUseCase
import com.test.network.backend.service.ProductsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    // / Provide Remote Data Source ///

    @Singleton
    @Provides
    fun provideProductsDataSource(
        productsApi: ProductsApi
    ): ProductsRemoteDataSource {
        return ProductsRemoteDataSource(productsApi)
    }

    // / Provide repositories  and use cases///

    @Singleton
    @Provides
    fun provideProductsRepository(
        productsRemoteDataSource: ProductsRemoteDataSource
    ): ProductsRepository {
        return ProductsRepository(productsRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideGetProductListUseCase(
        productsRepository: ProductsRepository
    ): GetProductListUseCase {
        return GetProductListUseCase(productsRepository)
    }
}