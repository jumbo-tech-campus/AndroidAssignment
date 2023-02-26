package com.test.network.backend.model.reponse.imageinfo

import com.squareup.moshi.Json

data class ImageResponse(
    @field:Json(name = "url") val url: String,
    @field:Json(name = "width") val width: Int,
    @field:Json(name = "height") val height: Int
)