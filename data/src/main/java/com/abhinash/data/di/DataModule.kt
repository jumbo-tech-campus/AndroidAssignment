package com.abhinash.data.di

import com.abhinash.data.repository.ProductRepositoryImpl
import com.abhinash.data.usecase.GetProductsUseCaseImpl
import com.abhinash.domain.repository.ProductRepository
import com.abhinash.domain.usecase.GetProductsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {
    @Binds
    @Singleton
    fun bindGetProductsUseCase(
        getProductsUseCaseImpl: GetProductsUseCaseImpl
    ): GetProductsUseCase

    @Binds
    @Singleton
    fun bindProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository
}