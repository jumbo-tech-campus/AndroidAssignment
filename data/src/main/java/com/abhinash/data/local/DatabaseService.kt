package com.abhinash.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abhinash.data.local.DatabaseService.Companion.DATABASE_VERSION
import com.abhinash.data.local.dao.ProductsDao
import com.abhinash.data.local.entity.CartProductStorageModel

@Database(
    entities =
    [CartProductStorageModel::class],
    exportSchema = true,
    version = DATABASE_VERSION)
abstract class DatabaseService : RoomDatabase() {

    abstract fun productsDao(): ProductsDao

    companion object {
        const val DATABASE_VERSION = 1
    }
}