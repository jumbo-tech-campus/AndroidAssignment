package com.abhinash.data.di

import android.content.Context
import androidx.room.Room
import com.abhinash.data.local.DatabaseService
import com.abhinash.data.repository.ProductRepositoryImpl
import com.abhinash.data.usecase.AddToCartUseCaseImpl
import com.abhinash.data.usecase.GetCartProductsImpl
import com.abhinash.data.usecase.GetProductsUseCaseImpl
import com.abhinash.data.usecase.RemoveFromCartUseCaseImpl
import com.abhinash.domain.repository.ProductRepository
import com.abhinash.domain.usecase.AddToCartUseCase
import com.abhinash.domain.usecase.GetCartProducts
import com.abhinash.domain.usecase.GetProductsUseCase
import com.abhinash.domain.usecase.RemoveFromCartUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindGetProductsUseCase(
        getProductsUseCaseImpl: GetProductsUseCaseImpl
    ): GetProductsUseCase

    @Binds
    @Singleton
    abstract fun bindGetCartProductsUseCase(
        getCartProductsImpl: GetCartProductsImpl
    ): GetCartProducts

    @Binds
    @Singleton
    abstract fun bindAddToCartUseCase(
        addToCartUseCaseImpl: AddToCartUseCaseImpl
    ): AddToCartUseCase

    @Binds
    @Singleton
    abstract fun bindRemoveFromCartUseCase(
        removeFromCartUseCaseImpl: RemoveFromCartUseCaseImpl
    ): RemoveFromCartUseCase

    @Binds
    @Singleton
    abstract fun bindProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

    companion object {
        @Provides
        @Singleton
        fun provideDatabaseService(@ApplicationContext context: Context): DatabaseService {
            return Room.databaseBuilder(context, DatabaseService::class.java, "jumbo-db").build()
        }
    }
}