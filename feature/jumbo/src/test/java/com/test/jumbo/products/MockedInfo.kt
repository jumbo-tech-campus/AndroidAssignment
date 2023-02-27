package com.test.jumbo.products

import com.test.network.backend.model.reponse.ProductResponse
import com.test.network.backend.model.reponse.imageinfo.ImageInfoResponse
import com.test.network.backend.model.reponse.imageinfo.ImageResponse
import com.test.network.backend.model.reponse.price.CurrencyResponse
import com.test.network.backend.model.reponse.price.PricesResponse
import com.test.network.backend.model.reponse.price.UnitPriceResponse

fun returnMockedProductResponse() = ProductResponse(
    id = "153500PAK",
    title = "Snickers chocolade repen 5 stuks",
    available = true,
    productType = "PartOfRetailSet",
    nixProduct = false,
    quantity = "5 x 50 g",
    topLevelCategory = "Koek, gebak, snoep, chips",
    topLevelCategoryId = "SG9",
    imageInfo = ImageInfoResponse(
        primaryView = listOf(
            ImageResponse(
                width = 180,
                height = 180,
                url = "test/url"
            ),
            ImageResponse(
                width = 180,
                height = 180,
                url = "test/url2"
            ),
            ImageResponse(
                width = 180,
                height = 180,
                url = "test/url3"
            ),
            ImageResponse(
                width = 180,
                height = 180,
                url = "test/url4"
            )
        )
    ),
    prices = PricesResponse(
        price = CurrencyResponse(currency = "EUR", amount = 298),
        UnitPriceResponse(
            unit = "KG",
            price = CurrencyResponse(currency = "EUR", amount = 100)
        )
    )
)