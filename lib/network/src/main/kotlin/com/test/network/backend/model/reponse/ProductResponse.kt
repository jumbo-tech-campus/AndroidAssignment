package com.test.network.backend.model.reponse

import com.squareup.moshi.Json
import com.test.network.backend.model.reponse.imageinfo.ImageInfoResponse
import com.test.network.backend.model.reponse.price.PricesResponse

data class ProductResponse(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "available") val available: Boolean,
    @field:Json(name = "productType") val productType: String,
    @field:Json(name = "nixProduct") val nixProduct: Boolean,
    @field:Json(name = "quantity") val quantity: String,
    @field:Json(name = "topLevelCategory") val topLevelCategory: String,
    @field:Json(name = "topLevelCategoryId") val topLevelCategoryId: String,
    @field:Json(name = "imageInfo") val imageInfo: ImageInfoResponse,
    @field:Json(name = "prices") val prices: PricesResponse
)