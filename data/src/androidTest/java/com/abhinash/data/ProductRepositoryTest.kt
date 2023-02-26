package com.abhinash.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abhinash.data.local.DatabaseService
import com.abhinash.data.model.ProductListDto
import com.abhinash.data.repository.ProductRepositoryImpl
import com.abhinash.domain.models.CartProduct
import com.abhinash.domain.models.Product
import com.abhinash.domain.repository.ProductRepository
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ProductRepositoryTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var databaseService: DatabaseService
    private lateinit var productList: List<Product>

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        databaseService = Room.inMemoryDatabaseBuilder(
            context, DatabaseService::class.java
        ).allowMainThreadQueries().build()

        // Load some products for testing
        val jsonString = context.assets.open("products.json").bufferedReader().use { it.readText() }
        val productListDto: ProductListDto = Gson().fromJson(jsonString, ProductListDto::class.java)
        productList = productListDto.products.map { it.toDomain() }

        productRepository = ProductRepositoryImpl(context, databaseService)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        if (::databaseService.isInitialized) {
            databaseService.close()
        }
    }

    @Test
    fun testLoadProducts() = runBlocking {
        // Load the products and verify that the returned list is not empty
        val products = productRepository.loadProducts()
        assertThat(products).isNotEmpty()
    }

    @Test
    fun testSaveProductToCart() = runBlocking {
        // Create a product to save to the cart
        val product = productList[0]

        // Save the product to the cart
        productRepository.saveProductToCart(product, 1)

        // Verify that the product is saved to the cart
        val cartProducts = productRepository.getProductsForCart().first()
        assertEquals(1, cartProducts.size)
        assertEquals(product.id, cartProducts[0].id)
        assertEquals(product.title, cartProducts[0].title)
        assertEquals(1, cartProducts[0].quantity)
    }

    @Test
    fun testGetProductsForCart() = runBlocking {
        // Save some products to the cart
        productRepository.saveProductToCart(productList[0], 1)
        productRepository.saveProductToCart(productList[1], 2)

        // Get the cart products and verify that the list is not empty and the products are correct
        val cartProducts = productRepository.getProductsForCart().first()
        assertThat(cartProducts).isNotEmpty()
        assertEquals(cartProducts.size, 2)
        assertEquals(cartProducts[0].id, productList[0].id)
        assertEquals(cartProducts[0].quantity, 1)
        assertEquals(cartProducts[1].id, (productList[1].id))
        assertEquals(cartProducts[1].quantity, 2)
    }

    @Test
    fun removeProductFromCart_removesProductFromDatabase() = runBlocking {
        // Given a cart product in the database
        val product = productList[0]
        val cartProduct = CartProduct.fromProduct(product, 0)
        productRepository.saveProductToCart(product, 1)

        // When the product is removed from the cart
        productRepository.removeProductFromCart(cartProduct)

        // Then the product should be removed from the database
        val cartProducts = productRepository.getProductsForCart().first()
        assertThat(cartProducts).isEmpty()
    }

    @Test
    fun removeProductFromCart_emitsNewProductListState() = runBlocking {
        // Given a cart product in the database
        val product = productList[0]
        val cartProduct = CartProduct.fromProduct(product, 0)
        productRepository.saveProductToCart(product, 1)

        // When the product is removed from the cart
        productRepository.removeProductFromCart(cartProduct)

        // Then a new product list state should be emitted
        val productFlow = productRepository.getProductsForCart()
        val productListState = productFlow.first()
        assertThat(productListState).isEmpty()

        // Add a new cart product and check that the new product list state is emitted
        val newProduct = productList[1]
        productRepository.saveProductToCart(newProduct, 1)

        val newProductListState = productFlow.first()
        assertEquals(listOf(newProduct).size, newProductListState.size)
        assertEquals(listOf(newProduct).first().id, newProductListState.first().id)
    }
}