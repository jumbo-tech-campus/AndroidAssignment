package com.assignment.jumboshop.di

import com.assignment.domain.repository.CartRepository
import com.assignment.domain.repository.ProductRepository
import com.assignment.domain.usecases.AddToCartUseCase
import com.assignment.domain.usecases.ClearCartUseCase
import com.assignment.domain.usecases.DecrementCartItemUseCase
import com.assignment.domain.usecases.GetCartItemsUseCase
import com.assignment.domain.usecases.GetProductsUseCase
import com.assignment.domain.usecases.IncrementCartItemUseCase
import com.assignment.domain.usecases.DeleteCartItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideGetProductsUseCase(productRepository: ProductRepository): GetProductsUseCase {
        return GetProductsUseCase(productRepository)
    }

    @Provides
    @Singleton
    fun provideAddToCartUseCase(cartRepository: CartRepository): AddToCartUseCase {
        return AddToCartUseCase(cartRepository)
    }

    @Provides
    @Singleton
    fun provideGetCartItemsUseCase(cartRepository: CartRepository): GetCartItemsUseCase {
        return GetCartItemsUseCase(cartRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteCartItemUseCase(cartRepository: CartRepository): DeleteCartItemUseCase {
        return DeleteCartItemUseCase(cartRepository)
    }
    @Provides
    @Singleton
    fun provideClearCartUseCase(cartRepository: CartRepository): ClearCartUseCase {
        return ClearCartUseCase(cartRepository)
    }
    @Provides
    @Singleton
    fun provideIncrementCartItemUseCase(cartRepository: CartRepository): IncrementCartItemUseCase {
        return IncrementCartItemUseCase(cartRepository)
    }

    @Provides
    @Singleton
    fun provideDecrementCartItemUseCase(cartRepository: CartRepository): DecrementCartItemUseCase {
        return DecrementCartItemUseCase(cartRepository)
    }
}
