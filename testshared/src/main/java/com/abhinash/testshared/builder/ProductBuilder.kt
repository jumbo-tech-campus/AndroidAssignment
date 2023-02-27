package com.abhinash.testshared.builder

import com.abhinash.domain.models.Product
import com.abhinash.domain.models.ProductImage
import com.abhinash.domain.models.ProductPrices
import com.appmattus.kotlinfixture.Fixture

class ProductBuilder {
    private var fixture = Fixture()
    private var id: String? = null
    private var title: String? = null
    private var productPrices: ProductPrices? = null
    private var productImage: ProductImage? = null
    private var quantity: String? = null

    fun withId(id: String): ProductBuilder {
        this.id = id
        return this
    }

    fun build(): Product =
        Product(
            id = this.id ?: fixture(),
            title = this.title ?: fixture(),
            productPrices = this.productPrices ?: fixture(),
            productImage = this.productImage ?: fixture(),
            quantity = this.quantity ?: fixture(),
        )
}