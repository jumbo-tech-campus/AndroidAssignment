package com.test.network.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.test.network.local.entities.CartItemEntity

const val MIN_ITEM_ON_DB = 1

@Dao
interface ProductsDao {

    @Insert
    suspend fun insertProduct(cartItem: CartItemEntity): Long

    @Delete
    suspend fun delete(cartItem: CartItemEntity)

    @Query("SELECT * FROM CartItemEntity WHERE uid= :id")
    suspend fun getItemById(id: String): List<CartItemEntity>

    @Query("UPDATE CartItemEntity SET quantity = quantity + 1 WHERE uid = :id")
    suspend fun increaseQuantityToProduct(id: String): Int

    @Query("UPDATE CartItemEntity SET quantity = quantity - 1 WHERE uid = :id")
    suspend fun decreaseQuantityToProduct(id: String): Int

    @Query("SELECT * FROM CartItemEntity")
    suspend fun getCart(): List<CartItemEntity>

    suspend fun deleteOrUpdate(cartItem: CartItemEntity): Long {
        val itemsFromDB = getItemById(cartItem.uid)
        return if (itemsFromDB.first().quantity > MIN_ITEM_ON_DB) {
            decreaseQuantityToProduct(cartItem.uid).toLong()
        } else {
            delete(cartItem)
            return 1L
        }
    }

    suspend fun insertOrUpdate(cartItem: CartItemEntity): Long {
        val itemsFromDB = getItemById(cartItem.uid)
        return if (itemsFromDB.isEmpty()) {
            insertProduct(cartItem)
        } else {
            increaseQuantityToProduct(cartItem.uid).toLong()
        }
    }
}