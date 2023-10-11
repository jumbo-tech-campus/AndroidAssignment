package dev.sierov.cart

import kotlinx.coroutines.flow.Flow

interface ReadOnlyCart {
    val content: Flow<ShoppingContent>
}

interface Cart : ReadOnlyCart {
    suspend fun putProduct(productId: String, quantity: Int = 1)
    suspend fun removeProduct(productId: String, quantity: Int = 1)
    suspend fun clearAll()
}

data class ShoppingContent(
    private val productToQuantity: Map<String, Int>,
) : Map<String, Int> by productToQuantity {
    override operator fun get(key: String): Int = getQuantity(productId = key)
    override fun containsKey(key: String): Boolean = getQuantity(key) > 0
    fun getQuantity(productId: String) = productToQuantity[productId] ?: 0

    companion object {
        val Empty = ShoppingContent(emptyMap())
    }
}