package com.abhinash.jumboandroidassignment.provider

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import javax.inject.Inject

interface ImageLoaderProvider {
    fun load(imageView: ImageView, url: String, @DrawableRes placeHolderResId: Int)

    fun cancel(imageView: ImageView)
}

class GlideImageLoaderProvider @Inject constructor() : ImageLoaderProvider {
    override fun load(imageView: ImageView, url: String, @DrawableRes placeHolderResId: Int) {
        Glide.with(imageView)
            .load(url)
            .placeholder(placeHolderResId)
            .into(imageView)
    }

    override fun cancel(imageView: ImageView) {
        Glide.with(imageView).clear(imageView)
    }
}