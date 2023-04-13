package com.assignment.data.api

import com.assignment.data.api.models.ProductResponse
import com.assignment.data.models.CurrencyAmountDataModel
import com.assignment.data.models.ImageInfoDataModel
import com.assignment.data.models.ImageSizeDataModel
import com.assignment.data.models.PricesDataModel
import com.assignment.data.models.ProductDataModel
import com.assignment.data.models.UnitPriceDataModel

object ProductResponseMapper {
    fun toDataModel(productResponse: ProductResponse): ProductDataModel {
        return ProductDataModel(
            id = productResponse.id,
            title = productResponse.title,
            prices = PricesDataModel(
                price = CurrencyAmountDataModel(
                    currency = productResponse.prices.price.currency,
                    amount = productResponse.prices.price.amount,
                ),
                unitPrice = UnitPriceDataModel(
                    unit = productResponse.prices.unitPrice.unit,
                    price = CurrencyAmountDataModel(
                        currency = productResponse.prices.unitPrice.price.currency,
                        amount = productResponse.prices.unitPrice.price.amount,
                    ),
                ),
            ),
            quantity = productResponse.quantity,
            imageInfo = ImageInfoDataModel(
                primaryView = productResponse.imageInfo.primaryView.map {
                    ImageSizeDataModel(
                        url = it.url,
                        width = it.width,
                        height = it.height,
                    )
                },
            ),
        )
    }

    fun toDataModel(productResponse: List<ProductResponse>): List<ProductDataModel> {
        return productResponse.map { toDataModel(it) }
    }
}
