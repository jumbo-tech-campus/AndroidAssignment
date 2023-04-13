package com.assignment.data.datasource.local.product

import android.util.Log
import com.assignment.data.datasource.local.mappers.ProductEntityMapper
import com.assignment.data.models.ProductDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor(private val productDao: ProductDao){
  fun getProducts(): Flow<List<ProductDataModel>>{
        return productDao.getProducts()
            .map { productsEntity ->
                ProductEntityMapper.entityListToModelDataList(productsEntity)
            }
    }

    suspend fun deleteAllProducts() {
       productDao.deleteAllProducts()
    }

    suspend fun insertProducts(apiProducts: List<ProductDataModel>) {
        val productsEntity = ProductEntityMapper.modelListToEntityList(apiProducts)
        productDao.insertProducts(productsEntity)
    }
}
