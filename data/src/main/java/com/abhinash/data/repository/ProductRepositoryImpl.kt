package com.abhinash.data.repository

import android.content.Context
import com.abhinash.data.local.DatabaseService
import com.abhinash.data.local.entity.CartProductStorageModel
import com.abhinash.data.model.ProductListDto
import com.abhinash.domain.models.CartProduct
import com.abhinash.domain.models.Product
import com.abhinash.domain.repository.ProductRepository
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val databaseService: DatabaseService
) : ProductRepository {
    private val productListState = MutableStateFlow<List<CartProduct>>(emptyList())
    override suspend fun loadProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            val jsonString =
                context.assets.open("products.json").bufferedReader().use { it.readText() }
            val productList: ProductListDto =
                Gson().fromJson(jsonString, ProductListDto::class.java)
            return@withContext productList.products.map { it.toDomain() }
        }
    }

    override suspend fun saveProductToCart(product: Product, quantity: Int) {
        withContext(Dispatchers.IO) {
            val cartProduct = CartProduct.fromProduct(product, quantity)
            databaseService.productsDao().insert(CartProductStorageModel.fromDomain(cartProduct))

            val cartProducts = databaseService.productsDao().getCartProducts()
            cartProducts?.let {
                productListState.emit(it.map { cartProduct -> cartProduct.toDomain() })
            }
        }
    }

    override fun getProductsForCart(): StateFlow<List<CartProduct>> =
        productListState.asStateFlow()

    override suspend fun getCartProductsCount() =
        withContext(Dispatchers.IO) {return@withContext databaseService.productsDao().countCartProducts() }

}