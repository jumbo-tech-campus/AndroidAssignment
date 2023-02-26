package com.abhinash.testshared.builder

import com.abhinash.domain.models.CartProduct
import com.appmattus.kotlinfixture.Fixture

class CartProductBuilder {
    private var fixture = Fixture()
    private var  id: String? = null
    private var  title: String? = null
    private var  unitPriceInEur: Double? = null
    private var  quantity: Int? = null
    private var  image: String? = null
    private var  size: Int? = null

    fun build(): CartProduct =
        CartProduct.load(
            id = this.id ?: fixture(),
            title = this.title ?: fixture(),
            unitPriceInEur = this.unitPriceInEur ?: fixture(),
            quantity = this.quantity ?: fixture(),
            image = this.image ?: fixture(),
            size = this.size ?: fixture()
        )
}