package com.test.network.backend.model.reponse.price

import com.squareup.moshi.Json

data class UnitPriceResponse(
    @field:Json(name = "unit") val unit: String,
    @field:Json(name = "price") val price: CurrencyResponse
)