package dev.sierov.cart.local

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import dev.sierov.cart.Cart
import dev.sierov.cart.ShoppingContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import okio.Path.Companion.toPath

typealias CartDatastoreDirectory = String

@Inject
class LocalDatastoreCart(directory: CartDatastoreDirectory) : Cart {

    private val datastore = PreferenceDataStoreFactory.createWithPath(
        produceFile = { "$directory/dev.sierov.cart.local.preferences_pb".toPath() },
        scope = CoroutineScope(context = Dispatchers.Default + SupervisorJob()),
        corruptionHandler = null,
        migrations = emptyList(),
    )

    override suspend fun putProduct(productId: String, quantity: Int) {
        datastore.edit {
            val key = intPreferencesKey(productId)
            val existingQuantity = it[key] ?: 0
            it += key to existingQuantity + quantity
        }
    }

    override suspend fun removeProduct(productId: String, quantity: Int) {
        datastore.edit {
            val key = intPreferencesKey(productId)
            val existingQuantity = it[key] ?: 0
            check(existingQuantity >= quantity) {
                "There's no $quantity product(s) to remove from the cart. " +
                        "Product(s) with id $productId in cart: $existingQuantity"
            }
            if (existingQuantity == 1) it.remove(key)
            else it += key to existingQuantity - quantity
        }
    }

    override suspend fun clearAll() {
        datastore.edit { it.clear() }
    }

    override val content: Flow<ShoppingContent> = datastore.data
        .map { it.asMap().map { (key, value) -> key.name to value.toString().toInt() } }
        .map { ShoppingContent(productToQuantity = it.toMap()) }
}