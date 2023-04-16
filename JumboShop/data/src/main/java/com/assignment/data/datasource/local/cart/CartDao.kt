package com.assignment.data.datasource.local.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assignment.data.datasource.local.entities.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("Select * from cart")
    fun getCartItems(): Flow<List<CartItemEntity>>

    @Query("Select * from cart WHERE id = :itemId")
    suspend fun getCartItem(itemId: String): CartItemEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cartItem: CartItemEntity)

    @Query("UPDATE cart SET quantity = quantity + 1 WHERE id = :itemId")
    suspend fun incrementCartItem(itemId: String)

    @Query("UPDATE cart SET quantity = quantity - 1 WHERE id = :itemId")
    suspend fun decrementCartItem(itemId: String)

    @Query("DELETE from cart WHERE id = :itemId")
    suspend fun deleteCartItem(itemId: String)

    @Query("DELETE from cart")
    suspend fun deleteAllCartItems()
}
