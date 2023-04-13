package com.assignment.jumboshop.ui.productlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.assignment.design_system.components.appbars.JumboAppBar
import com.assignment.design_system.components.texts.JumboBody
import com.assignment.domain.entities.Product
import com.assignment.jumboshop.ui.productlist.parts.ProductItem
import com.assignment.jumboshop.ui.productlist.parts.ShoppingCartFAB

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    state: ProductListUiState<List<Product>>,
    cartItemsCount: Int = 0,
    onAddToCart: (Product) -> Unit,
    openCart: () -> Unit = {},
    openDetails: (productId: String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            JumboAppBar(title = "Jumbo Shop")
        },
        floatingActionButton = {
            ShoppingCartFAB(cartItemsCount) {
                openCart()
            }
        },
    ) {paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues), contentAlignment = Alignment.Center) {
            when (state) {
                ProductListUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is ProductListUiState.Error -> {
                    JumboBody(text = state.exception.localizedMessage ?: "an error occurred!")
                }
                is ProductListUiState.Success -> {
                    val products = state.data
                    LazyVerticalGrid(columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(bottom = 84.dp)
                    ) {
                        items(products, key = { item -> item.id }) { product ->
                            ProductItem(product = product, onAddToCart = onAddToCart, onClick = {
                                openDetails(it.id)
                            })
                        }
                    }
                }
            }
        }
    }
}
