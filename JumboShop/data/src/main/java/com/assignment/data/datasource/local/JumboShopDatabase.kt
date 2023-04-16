package com.assignment.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.assignment.data.converters.Converters
import com.assignment.data.datasource.local.cart.CartDao
import com.assignment.data.datasource.local.entities.CartItemEntity
import com.assignment.data.datasource.local.entities.ProductEntity
import com.assignment.data.datasource.local.product.ProductDao

@Database(entities = [CartItemEntity::class, ProductEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class JumboShopDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: JumboShopDatabase? = null

        fun getDatabase(context: Context): JumboShopDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JumboShopDatabase::class.java,
                    "jumboshop_database",
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
