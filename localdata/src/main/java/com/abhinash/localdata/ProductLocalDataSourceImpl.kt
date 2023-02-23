package com.abhinash.localdata

import android.content.Context
import com.abhinash.data.contract.local.ProductLocalDataSource
import com.abhinash.domain.models.Product
import com.abhinash.localdata.model.ProductListStorageModel
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

internal class ProductLocalDataSourceImpl @Inject constructor(@ApplicationContext private val context: Context): ProductLocalDataSource {

    private val productListState = MutableStateFlow<List<Product>>(emptyList())

    override suspend fun importProducts() = coroutineScope {
        val jsonString = context.assets.open("products.json").bufferedReader().use { it.readText() }
        val productList: ProductListStorageModel = Gson().fromJson(jsonString, ProductListStorageModel::class.java)
        productListState.emit(productList.products)
    }

    override fun saveProduct(productId: String, quantity: Int) {
        TODO("Not yet implemented")
    }

    override fun loadProducts(): StateFlow<List<Product>> = productListState.asStateFlow()

    override fun removeProduct(productId: String) {
        TODO("Not yet implemented")
    }
}