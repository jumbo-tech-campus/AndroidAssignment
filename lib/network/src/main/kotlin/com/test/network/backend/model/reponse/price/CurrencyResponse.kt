package com.test.network.backend.model.reponse.price

import com.squareup.moshi.Json

data class CurrencyResponse(
    @field:Json(name = "currency") val currency: String,
    @field:Json(name = "amount") val amount: Int
)