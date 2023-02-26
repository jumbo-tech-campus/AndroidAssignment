package com.test.network.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.test.network.local.entities.CartEntity

@Dao
interface ProductsDao {

    @Query("SELECT * FROM CartEntity")
    suspend fun getCart(): List<CartEntity>

    @Insert
    suspend fun insertProduct(product: CartEntity): Long

    @Delete
    suspend fun delete(product: CartEntity)
}