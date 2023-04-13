package com.assignment.data.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductResponse(
    val id: String,
    val title: String,
    val prices: PricesResponse,
    val available: Boolean,
    val productType: String,
    val nixProduct: Boolean,
    val quantity: String,
    val imageInfo: ImageInfoResponse,
    val topLevelCategory: String,
    val topLevelCategoryId: String,
)

@JsonClass(generateAdapter = true)
data class PricesResponse(
    val price: CurrencyAmountResponse,
    @Json(name = "unitPrice")
    val unitPrice: UnitPriceResponse,
)

@JsonClass(generateAdapter = true)
data class CurrencyAmountResponse(
    val currency: String,
    val amount: Double,
)

@JsonClass(generateAdapter = true)
data class UnitPriceResponse(
    val unit: String,
    val price: CurrencyAmountResponse,
)

@JsonClass(generateAdapter = true)
data class ImageInfoResponse(
    @Json(name = "primaryView")
    val primaryView: List<ImageSizeResponse>,
)

@JsonClass(generateAdapter = true, generator = "")
data class ImageSizeResponse(
    val url: String,
    val width: Int,
    val height: Int,
)
