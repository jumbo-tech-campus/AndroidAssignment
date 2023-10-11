package dev.sierov.cart

import kotlinx.coroutines.flow.Flow

interface ReadOnlyCart {
    val content: Flow<ShoppingContent>
}

interface Cart : ReadOnlyCart {
    suspend fun putProduct(productId: String, quantity: Int = 1)
    suspend fun removeProduct(productId: String, quantity: Int = 1)
    suspend fun clearALl()
}

data class ShoppingContent(
    private val productToQuantity: Map<String, Int>,
) : Map<String, Int> by productToQuantity {
    override operator fun get(key: String): Int = productToQuantity[key] ?: 0
    override fun containsKey(key: String): Boolean = get(key) > 0

    companion object {
        val Empty = ShoppingContent(emptyMap())
    }
}