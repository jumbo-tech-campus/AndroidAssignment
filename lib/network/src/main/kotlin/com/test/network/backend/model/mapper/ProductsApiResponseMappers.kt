package com.test.network.backend.model.mapper

import com.test.model.Product
import com.test.model.Products
import com.test.network.backend.model.reponse.ProductResponse
import com.test.network.backend.model.reponse.ProductsResponse

fun ProductResponse.toProductModel() = Product(
    id = this.id,
    title = this.title,
    available = this.available,
    productType = this.productType,
    nixProduct = this.nixProduct,
    quantity = this.quantity,
    topLevelCategory = this.topLevelCategory,
    topLevelCategoryId = this.topLevelCategoryId,
    imageInfo = this.imageInfo.toImageInfoModel(),
    prices = this.prices.toPricesModel()
)

fun ProductsResponse.toProductModel() = Products(
   products = this.products.toProductListModel()
)

fun List<ProductResponse>.toProductListModel(): List<Product> {
    val listResult = mutableListOf<Product>()
    this.forEach {
        listResult.add(
            it.toProductModel()
        )
    }
    return listResult.toList()
}
