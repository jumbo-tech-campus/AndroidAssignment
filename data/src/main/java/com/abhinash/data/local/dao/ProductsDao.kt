package com.abhinash.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhinash.data.local.entity.CartProductStorageModel

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cartProductStorageModel: CartProductStorageModel)

    @Query("SELECT * FROM cart_products ORDER BY id")
    fun getCartProducts(): List<CartProductStorageModel>?

    @Query("SELECT COUNT(id) FROM cart_products")
    fun countCartProducts(): Int

    @Query("DELETE FROM cart_products WHERE id = :cartProductId")
    fun deleteCartProduct(cartProductId: String): Int
}