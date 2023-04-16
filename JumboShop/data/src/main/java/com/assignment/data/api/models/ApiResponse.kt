package com.assignment.data.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse(
    val products: List<ProductResponse>
)
