package com.abhinash.jumboandroidassignment.di

import com.abhinash.jumboandroidassignment.provider.GlideImageLoaderProvider
import com.abhinash.jumboandroidassignment.provider.ImageLoaderProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindImageLoaderProvider(glideImageLoaderProvider: GlideImageLoaderProvider): ImageLoaderProvider
}