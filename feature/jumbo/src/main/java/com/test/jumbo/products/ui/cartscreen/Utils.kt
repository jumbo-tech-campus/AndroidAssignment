package com.test.jumbo.products.ui.cartscreen

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency

fun formatPrice(price: Int, currencyCode: String): String {
    val numberFormat = NumberFormat.getCurrencyInstance()
    numberFormat.currency = Currency.getInstance(currencyCode)
    return NumberFormat.getCurrencyInstance().format(
        BigDecimal(price).movePointLeft(2)
    )
}