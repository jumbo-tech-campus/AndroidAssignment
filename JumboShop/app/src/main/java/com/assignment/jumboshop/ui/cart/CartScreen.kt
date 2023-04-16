package com.assignment.jumboshop.ui.cart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.assignment.design_system.components.texts.JumboHeader
import com.assignment.design_system.components.texts.JumboText
import com.assignment.design_system.components.texts.JumboTitle
import com.assignment.domain.entities.CartItem
import com.assignment.jumboshop.ui.cart.parts.CartItem
import com.assignment.jumboshop.ui.cart.parts.CheckOutCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    cartUiState: CartUiState,
    incrementItem: (CartItem) -> Unit,
    decrementItem: (CartItem) -> Unit,
    deleteItem: (CartItem) -> Unit,
    clearItems: () -> Unit,
    navBack: () -> Unit = {}
) {
    val cartItems = cartUiState.cartItems
    val totalCost = cartUiState.totalCost
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navBack()
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowLeft,
                            contentDescription = "Back to home"
                        )
                    }
                },
                title = {
                    Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.Center) {
                        JumboHeader(text = "Cart")
                    }
                }, actions = {
                    JumboText(modifier = Modifier
                        .clickable {
                            clearItems()
                        }
                        .padding(end = 16.dp), text = "Clear (${cartItems.size})",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), color = Color.Red)
                })
        },
        bottomBar = {
            CheckOutCard(cartItemsCount = cartItems.size, totalCost= totalCost, currency = cartItems.firstOrNull()?.currency ?: "EUR")
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize(), contentAlignment = Alignment.Center) {
            if(cartItems.isNotEmpty()) {
                LazyColumn(Modifier.fillMaxSize()) {
                    items(cartItems, key = { item -> item.id }) { cartItem ->
                        CartItem(cartItem = cartItem, increment = {
                            incrementItem(it)
                        }, decrement = {
                            decrementItem(it)
                        }, delete = {
                            deleteItem(it)
                        })
                    }
                }
            }else{
                JumboTitle(text = "Your cart is empty!")
            }
        }
    }
}
