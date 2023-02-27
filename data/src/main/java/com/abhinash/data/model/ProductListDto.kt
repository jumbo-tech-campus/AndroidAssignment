package com.abhinash.data.model

import com.google.gson.annotations.SerializedName

internal class ProductListDto(@SerializedName("products") val products: List<ProductDto>)