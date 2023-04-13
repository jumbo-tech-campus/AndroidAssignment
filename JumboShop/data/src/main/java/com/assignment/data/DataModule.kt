package com.assignment.data

import okhttp3.logging.HttpLoggingInterceptor
import android.content.Context
import com.assignment.data.api.ProductService
import com.assignment.data.datasource.local.cart.CartDao
import com.assignment.data.datasource.local.JumboShopDatabase
import com.assignment.data.datasource.local.cart.CartLocalDataSource
import com.assignment.data.datasource.local.product.ProductDao
import com.assignment.data.datasource.local.product.ProductLocalDataSource
import com.assignment.data.datasource.remote.ProductRemoteDataSource
import com.assignment.data.repository.CartRepositoryImpl
import com.assignment.data.repository.ProductRepositoryImpl
import com.assignment.domain.repository.CartRepository
import com.assignment.domain.repository.ProductRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private const val baseUrl = "https://raw.githubusercontent.com/jumbo-tech-campus/AndroidAssignment/"
    @Provides
    @Singleton
    fun provideProductService(): ProductService {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ProductService::class.java)
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideJumboShopDatabase(@ApplicationContext context: Context): JumboShopDatabase {
        return JumboShopDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideCartDao(jumboShopDatabase: JumboShopDatabase): CartDao {
        return jumboShopDatabase.cartDao()
    }
    @Provides
    @Singleton
    fun provideProductDao(jumboShopDatabase: JumboShopDatabase): ProductDao {
        return jumboShopDatabase.productDao()
    }
    @Provides
    @Singleton
    fun provideProductRemoteDataSource(productService: ProductService): ProductRemoteDataSource {
        return ProductRemoteDataSource(productService)
    }
    @Provides
    @Singleton
    fun provideProductLocalDataSource(productDao: ProductDao): ProductLocalDataSource {
        return ProductLocalDataSource(productDao)
    }
    @Provides
    @Singleton
    fun provideProductRepository(productRemoteDataSource: ProductRemoteDataSource, productLocalDataSource: ProductLocalDataSource): ProductRepository {
        return ProductRepositoryImpl(productRemoteDataSource,productLocalDataSource)
    }
    @Provides
    @Singleton
    fun provideCartRepository(cartLocalDataSource: CartLocalDataSource): CartRepository {
        return CartRepositoryImpl(cartLocalDataSource)
    }
}
