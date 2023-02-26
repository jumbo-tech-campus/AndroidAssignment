package com.test.network.backend.model.mapper

import com.test.model.imageinfo.Image
import com.test.model.imageinfo.ImageInfo
import com.test.model.price.Currency
import com.test.model.price.Prices
import com.test.model.price.UnitPrice
import com.test.network.backend.model.reponse.imageinfo.ImageInfoResponse
import com.test.network.backend.model.reponse.imageinfo.ImageResponse
import com.test.network.backend.model.reponse.price.CurrencyResponse
import com.test.network.backend.model.reponse.price.PricesResponse
import com.test.network.backend.model.reponse.price.UnitPriceResponse

fun ImageInfoResponse.toImageInfoModel() = ImageInfo(
    primaryView = this.primaryView.toImageInfoListModel()
)

fun PricesResponse.toPricesModel() = Prices(
    price = this.price.toCurrencyModel(),
    unitPrice = this.unitPrice.toUnitPriceModel()
)

fun UnitPriceResponse.toUnitPriceModel() = UnitPrice(
    unit = this.unit,
    price = this.price.toCurrencyModel()
)

fun CurrencyResponse.toCurrencyModel() = Currency(
    currency = this.currency,
    amount = this.amount
)

fun List<ImageResponse>.toImageInfoListModel()
        : List<Image> {
    val listResult = mutableListOf<Image>()
    this.forEach {
        listResult.add(
            it.toImageModel()
        )
    }
    return listResult.toList()
}

fun ImageResponse.toImageModel() = Image(
    width = this.width,
    height = this.height,
    url = this.url
)