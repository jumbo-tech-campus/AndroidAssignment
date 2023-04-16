package com.assignment.data.mappers

import com.assignment.data.models.CurrencyAmountDataModel
import com.assignment.data.models.ImageInfoDataModel
import com.assignment.data.models.ImageSizeDataModel
import com.assignment.data.models.PricesDataModel
import com.assignment.data.models.ProductDataModel
import com.assignment.data.models.UnitPriceDataModel
import com.assignment.domain.entities.CurrencyAmount
import com.assignment.domain.entities.ImageInfo
import com.assignment.domain.entities.ImageSize
import com.assignment.domain.entities.Prices
import com.assignment.domain.entities.Product
import com.assignment.domain.entities.UnitPrice

object ProductDataMapper {
    fun toDomain(productDataModel: ProductDataModel): Product {
        return Product(
            id = productDataModel.id,
            title = productDataModel.title,
            prices = Prices(
                price = CurrencyAmount(
                    currency = productDataModel.prices.price.currency,
                    amount = productDataModel.prices.price.amount,
                ),
                unitPrice = UnitPrice(
                    unit = productDataModel.prices.unitPrice.unit,
                    price = CurrencyAmount(
                        currency = productDataModel.prices.unitPrice.price.currency,
                        amount = productDataModel.prices.unitPrice.price.amount,
                    ),
                ),
            ),
            quantity = productDataModel.quantity,
            imageInfo = ImageInfo(
                primaryView = productDataModel.imageInfo.primaryView.map {
                    ImageSize(
                        url = it.url,
                        width = it.width,
                        height = it.height,
                    )
                },
            ),
        )
    }

    fun toDomain(productDataModels: List<ProductDataModel>): List<Product> {
        return productDataModels.map { toDomain(it) }
    }

    fun toDataModel(product: Product): ProductDataModel {
        return ProductDataModel(
            id = product.id,
            title = product.title,
            prices = PricesDataModel(
                price = CurrencyAmountDataModel(
                    currency = product.prices.price.currency,
                    amount = product.prices.price.amount,
                ),
                unitPrice = UnitPriceDataModel(
                    unit = product.prices.unitPrice.unit,
                    price = CurrencyAmountDataModel(
                        currency = product.prices.unitPrice.price.currency,
                        amount = product.prices.unitPrice.price.amount,
                    ),
                ),
            ),
            quantity = product.quantity,
            imageInfo = ImageInfoDataModel(
                primaryView = product.imageInfo.primaryView.map {
                    ImageSizeDataModel(
                        url = it.url,
                        width = it.width,
                        height = it.height,
                    )
                },
            ),
        )
    }
}
