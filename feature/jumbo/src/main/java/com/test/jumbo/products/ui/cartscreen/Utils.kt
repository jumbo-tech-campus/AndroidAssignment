package com.test.jumbo.products.ui.cartscreen

import java.math.BigDecimal
import java.text.NumberFormat

fun formatPrice(price: Int): String {
    return NumberFormat.getCurrencyInstance().format(
        BigDecimal(price).movePointLeft(2)
    )
}