package com.abhinash.localdata.di

import android.content.Context
import com.abhinash.data.contract.local.ProductLocalDataSource
import com.abhinash.localdata.ProductLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalDataModule{
    companion object{
        @Singleton
        @Provides
        fun provideProductLocalDataSource(@ApplicationContext context: Context):ProductLocalDataSource =
            ProductLocalDataSourceImpl(context)
    }
    @Binds
    @Singleton
    abstract fun bindProductLocalDataSource(
        productLocalDataSourceImpl: ProductLocalDataSourceImpl
    ): ProductLocalDataSource
}