package com.assignment.jumboshop.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule{
    @Provides
    @Singleton
    @IO
    fun provideIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    @DEFAULT
    fun provideDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }
    @Provides
    @Singleton
    @MAIN
    fun provideMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IO

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MAIN

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DEFAULT
