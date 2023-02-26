package com.test.network.backend.model.reponse.imageinfo

import com.squareup.moshi.Json

data class ImageInfoResponse(
    @field:Json(name = "primaryView") val primaryView: List<ImageResponse>
)