package com.assignment.data.datasource.local.mappers

import com.assignment.data.datasource.local.entities.CurrencyAmountEntity
import com.assignment.data.datasource.local.entities.ImageInfoEntity
import com.assignment.data.datasource.local.entities.ImageSizeEntity
import com.assignment.data.datasource.local.entities.PricesEntity
import com.assignment.data.datasource.local.entities.ProductEntity
import com.assignment.data.datasource.local.entities.UnitPriceEntity
import com.assignment.data.models.CurrencyAmountDataModel
import com.assignment.data.models.ImageInfoDataModel
import com.assignment.data.models.ImageSizeDataModel
import com.assignment.data.models.PricesDataModel
import com.assignment.data.models.ProductDataModel
import com.assignment.data.models.UnitPriceDataModel

object ProductEntityMapper {
    fun entityToModelData(productEntity: ProductEntity): ProductDataModel {
        return ProductDataModel(
            id = productEntity.id,
            title = productEntity.title,
            prices = pricesEntityToDataModel(productEntity.prices),
            quantity = productEntity.quantity,
            imageInfo = imageInfoEntityToDataModel(productEntity.imageInfo)
        )
    }

    fun modelToEntity(productData: ProductDataModel): ProductEntity {
        return ProductEntity(
            id = productData.id,
            title = productData.title,
            prices = pricesDataModelToEntity(productData.prices),
            quantity = productData.quantity,
            imageInfo = imageInfoDataModelToEntity(productData.imageInfo)
        )
    }

    fun entityListToModelDataList(productEntities: List<ProductEntity>): List<ProductDataModel> {
        return productEntities.map { entityToModelData(it) }
    }

    fun modelListToEntityList(productData: List<ProductDataModel>): List<ProductEntity> {
        return productData.map { modelToEntity(it) }
    }
    private fun pricesDataModelToEntity(pricesDataModel: PricesDataModel): PricesEntity {
        return PricesEntity(
            price = currencyAmountDataModelToEntity(pricesDataModel.price),
            unitPrice = unitPriceDataModelToEntity(pricesDataModel.unitPrice)
        )
    }

    private fun currencyAmountDataModelToEntity(currencyAmountDataModel: CurrencyAmountDataModel): CurrencyAmountEntity {
        return CurrencyAmountEntity(
            currency = currencyAmountDataModel.currency,
            amount = currencyAmountDataModel.amount
        )
    }

    private fun unitPriceDataModelToEntity(unitPriceDataModel: UnitPriceDataModel): UnitPriceEntity {
        return UnitPriceEntity(
            unit = unitPriceDataModel.unit,
            price = currencyAmountDataModelToEntity(unitPriceDataModel.price)
        )
    }

    private fun imageInfoDataModelToEntity(imageInfoDataModel: ImageInfoDataModel): ImageInfoEntity {
        return ImageInfoEntity(
            primaryView = imageInfoDataModel.primaryView.map { imageSizeDataModelToEntity(it) }
        )
    }

    private fun imageSizeDataModelToEntity(imageSizeDataModel: ImageSizeDataModel): ImageSizeEntity {
        return ImageSizeEntity(
            url = imageSizeDataModel.url,
            width = imageSizeDataModel.width,
            height = imageSizeDataModel.height
        )
    }

    // Add additional methods to convert from domain models to data models if needed.

    private fun pricesEntityToDataModel(pricesEntity: PricesEntity): PricesDataModel {
        return PricesDataModel(
            price = currencyAmountEntityToDataModel(pricesEntity.price),
            unitPrice = unitPriceEntityToDataModel(pricesEntity.unitPrice)
        )
    }
    private fun currencyAmountEntityToDataModel(currencyAmountEntity: CurrencyAmountEntity): CurrencyAmountDataModel {
        return CurrencyAmountDataModel(
            currency = currencyAmountEntity.currency,
            amount = currencyAmountEntity.amount
        )
    }

    private fun unitPriceEntityToDataModel(unitPriceEntity: UnitPriceEntity): UnitPriceDataModel {
        return UnitPriceDataModel(
            unit = unitPriceEntity.unit,
            price = currencyAmountEntityToDataModel(unitPriceEntity.price)
        )
    }

    private fun imageInfoEntityToDataModel(imageInfoEntity: ImageInfoEntity): ImageInfoDataModel {
        return ImageInfoDataModel(
            primaryView = imageInfoEntity.primaryView.map { imageSizeEntityToDataModel(it) }
        )
    }

    private fun imageSizeEntityToDataModel(imageSizeEntity: ImageSizeEntity): ImageSizeDataModel {
        return ImageSizeDataModel(
            url = imageSizeEntity.url,
            width = imageSizeEntity.width,
            height = imageSizeEntity.height
        )
    }

}
