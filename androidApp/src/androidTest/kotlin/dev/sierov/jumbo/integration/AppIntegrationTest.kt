@file:OptIn(ExperimentalTestApi::class)

package dev.sierov.jumbo.integration

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import dev.sierov.jumbo.App
import dev.sierov.jumbo.MainActivity
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppIntegrationTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        val assets = getInstrumentation().context.resources.assets
        val products = assets.open("JumboProducts.json").bufferedReader().use { it.readText() }
        val app = getInstrumentation().targetContext.applicationContext as App
        app.server.enqueue(MockResponse().setBody(products).setHeader("Content-Type", "text/*"))
    }

    @Test
    fun monkeyProductsAndCartIntegration() {
        composeRule.onNode(startShopping)
            .assertExists()
            .performClick()

        composeRule.onNode(productsNavigationTab)
            .assertIsSelected()

        composeRule.waitUntilAtLeastOneExists(snickersProduct)

        composeRule.onNode(snickersProduct)
            .onChildren()
            .filterToOne(addToCart)
            .performClick() // adding
            .performClick() // three
            .performClick() // items

        composeRule.onNode(cartNavigationTab)
            .performClick()

        composeRule.onNode(snickersProduct)
            .onChildren()
            .filterToOne(removeFromCart)
            .performClick() // removing
            .performClick() // three
            .performClick() // items

        composeRule.onNode(snickersProduct)
            .assertIsNotDisplayed()
    }
}

private val startShopping
    get() = hasText("Start Shopping")

private val cartNavigationTab
    get() = hasText("Cart")

private val productsNavigationTab
    get() = hasText("Products")

private val addToCart
    get() = hasContentDescription("+1")

private val removeFromCart
    get() = hasContentDescription("-1")

private val snickersProduct
    get() = hasContentDescription("Snickers 50gr") and
            hasAnyChild(hasContentDescription("+1"))