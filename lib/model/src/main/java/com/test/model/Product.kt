package com.test.model

import com.test.model.imageinfo.ImageInfo
import com.test.model.price.Prices

data class Product(
    val id: String,
    val title: String,
    val available: Boolean,
    val productType: String,
    val nixProduct: Boolean,
    val quantity: String,
    val topLevelCategory: String,
    val topLevelCategoryId: String,
    val imageInfo: ImageInfo,
    val prices: Prices
)