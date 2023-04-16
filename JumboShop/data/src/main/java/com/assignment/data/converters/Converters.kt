package com.assignment.data.converters

import androidx.room.TypeConverter
import com.assignment.data.DataModule
import com.assignment.data.datasource.local.entities.CurrencyAmountEntity
import com.assignment.data.datasource.local.entities.ImageInfoEntity
import com.assignment.data.datasource.local.entities.ImageSizeEntity
import com.assignment.data.datasource.local.entities.PricesEntity
import com.assignment.data.datasource.local.entities.UnitPriceEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types

class Converters {

    private val moshi = DataModule.provideMoshi()
        @TypeConverter
        fun fromPricesEntity(pricesEntity: PricesEntity): String {
            val adapter: JsonAdapter<PricesEntity> = moshi.adapter(PricesEntity::class.java)
            return adapter.toJson(pricesEntity)
        }

        @TypeConverter
        fun toPricesEntity(pricesEntityJson: String): PricesEntity {
            val adapter: JsonAdapter<PricesEntity> = moshi.adapter(PricesEntity::class.java)
            return adapter.fromJson(pricesEntityJson)!!
        }

        @TypeConverter
        fun fromImageInfoEntity(imageInfoEntity: ImageInfoEntity): String {
            val adapter: JsonAdapter<ImageInfoEntity> = moshi.adapter(ImageInfoEntity::class.java)
            return adapter.toJson(imageInfoEntity)
        }

        @TypeConverter
        fun toImageInfoEntity(imageInfoEntityJson: String): ImageInfoEntity {
            val adapter: JsonAdapter<ImageInfoEntity> = moshi.adapter(ImageInfoEntity::class.java)
            return adapter.fromJson(imageInfoEntityJson)!!
        }

        @TypeConverter
        fun fromCurrencyAmountEntity(currencyAmountEntity: CurrencyAmountEntity): String {
            val adapter: JsonAdapter<CurrencyAmountEntity> = moshi.adapter(CurrencyAmountEntity::class.java)
            return adapter.toJson(currencyAmountEntity)
        }

        @TypeConverter
        fun toCurrencyAmountEntity(currencyAmountEntityJson: String): CurrencyAmountEntity {
            val adapter: JsonAdapter<CurrencyAmountEntity> = moshi.adapter(CurrencyAmountEntity::class.java)
            return adapter.fromJson(currencyAmountEntityJson)!!
        }

        @TypeConverter
        fun fromUnitPriceEntity(unitPriceEntity: UnitPriceEntity): String {
            val adapter: JsonAdapter<UnitPriceEntity> = moshi.adapter(UnitPriceEntity::class.java)
            return adapter.toJson(unitPriceEntity)
        }

        @TypeConverter
        fun toUnitPriceEntity(unitPriceEntityJson: String): UnitPriceEntity {
            val adapter: JsonAdapter<UnitPriceEntity> = moshi.adapter(UnitPriceEntity::class.java)
            return adapter.fromJson(unitPriceEntityJson)!!
        }

        @TypeConverter
        fun fromImageSizeEntityList(imageSizeEntityList: List<ImageSizeEntity>): String {
            val type = Types.newParameterizedType(List::class.java, ImageSizeEntity::class.java)
            val adapter: JsonAdapter<List<ImageSizeEntity>> = moshi.adapter(type)
            return adapter.toJson(imageSizeEntityList)
        }

        @TypeConverter
        fun toImageSizeEntityList(imageSizeEntityListJson: String): List<ImageSizeEntity> {
            val type = Types.newParameterizedType(List::class.java, ImageSizeEntity::class.java)
            val adapter: JsonAdapter<List<ImageSizeEntity>> = moshi.adapter(type)
            return adapter.fromJson(imageSizeEntityListJson)!!
        }
    }
