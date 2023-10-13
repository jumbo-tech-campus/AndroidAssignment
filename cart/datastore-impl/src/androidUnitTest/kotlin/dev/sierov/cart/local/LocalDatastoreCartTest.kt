package dev.sierov.cart.local

import app.cash.turbine.test
import dev.sierov.cart.ShoppingContent
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LocalDatastoreCartTest {

    @get:Rule
    val temporaryFolder = TemporaryFolder()

    @Test
    fun `put and remove products to and from cart`() = runTest {
        val cart = LocalDatastoreCart(temporaryFolder.root.path)
        cart.content.test {
            assertEquals(ShoppingContent.Empty, awaitItem())

            cart.putProduct("product1")
            val shoppingCartWithOneProduct = awaitItem()
            assertTrue("product1" in shoppingCartWithOneProduct)
            assertTrue(shoppingCartWithOneProduct.getQuantity("product1") == 1)

            cart.putProduct("product2")
            val shoppingCartWithTwoProducts = awaitItem()
            assertTrue("product1" in shoppingCartWithTwoProducts)
            assertTrue("product2" in shoppingCartWithTwoProducts)
            assertTrue(shoppingCartWithTwoProducts.getQuantity("product1") == 1)
            assertTrue(shoppingCartWithTwoProducts.getQuantity("product2") == 1)

            cart.removeProduct("product1")
            val shoppingCartWithoutFirstProduct = awaitItem()
            assertFalse("product1" in shoppingCartWithoutFirstProduct)
            assertTrue("product2" in shoppingCartWithoutFirstProduct)
            assertTrue(shoppingCartWithoutFirstProduct["product1"] == 0) // alias []
            assertTrue(shoppingCartWithoutFirstProduct["product2"] == 1) // alias []

            cart.removeProduct("product2")
            val emptyShoppingCart = awaitItem()
            assertEquals(ShoppingContent.Empty, emptyShoppingCart)
        }
    }

    @Test(expected = IllegalStateException::class)
    fun `removing product throws when existing quantity is less than provided`() = runTest {
        val cart = LocalDatastoreCart(temporaryFolder.root.path)
        cart.putProduct("product", quantity = 5)
        cart.removeProduct("product", quantity = 6)
    }

    @Test(expected = IllegalStateException::class)
    fun `removing product throws when removing non-present product`() = runTest {
        val cart = LocalDatastoreCart(temporaryFolder.root.path)
        cart.putProduct("product")
        cart.removeProduct("non-present-product")
    }
}