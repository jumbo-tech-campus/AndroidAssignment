package com.test.network.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.network.local.dao.ProductsDao
import com.test.network.local.entities.CartEntity

@Database(entities = [CartEntity::class], version = 1)
abstract class ProductsDb : RoomDatabase() {
    abstract fun productDao(): ProductsDao
}