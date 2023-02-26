package com.test.network.backend.model.reponse.price

import com.squareup.moshi.Json

data class PricesResponse(
    @field:Json(name = "price") val price: CurrencyResponse,
    @field:Json(name = "unitPrice") val unitPrice: UnitPriceResponse
)