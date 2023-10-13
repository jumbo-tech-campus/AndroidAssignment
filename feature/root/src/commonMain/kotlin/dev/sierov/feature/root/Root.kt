package dev.sierov.feature.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.slack.circuit.backstack.SaveableBackStack
import com.slack.circuit.backstack.isAtRoot
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.Screen
import dev.sierov.cart.ReadOnlyCart
import dev.sierov.cart.ShoppingContent
import dev.sierov.screen.CartScreen
import dev.sierov.screen.ProductsScreen
import dev.sierov.screen.StartScreen

@Composable
internal fun Root(
    cart: ReadOnlyCart,
    backstack: SaveableBackStack,
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    val rootScreen by remember(backstack) {
        derivedStateOf { checkNotNull(backstack.topRecord?.screen) { "Top record is null" } }
    }

    val bottomBar = @Composable {
        BottomNavigationBar(
            cart = cart,
            selectedScreen = rootScreen,
            onNavigationSelected = { navigator.resetRootIfDifferent(it, backstack) }
        )
    }
    Scaffold(
        bottomBar = bottomBar,
        modifier = modifier
    ) { paddingValues ->
        NavigableCircuitContent(
            navigator = navigator,
            backstack = backstack,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun BottomNavigationBar(
    cart: ReadOnlyCart,
    selectedScreen: Screen,
    onNavigationSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
        windowInsets = WindowInsets.navigationBars,
    ) {
        NavigationBarItem(
            selected = selectedScreen is StartScreen,
            onClick = { onNavigationSelected(StartScreen) },
            label = { Text(text = "Start") },
            icon = { Icon(imageVector = Icons.Default.Menu, contentDescription = "Start") },
        )
        NavigationBarItem(
            selected = selectedScreen is ProductsScreen,
            label = { Text(text = "Products") },
            onClick = { onNavigationSelected(ProductsScreen) },
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = "Products") },
        )
        val shoppingContent by cart.content.collectAsState(ShoppingContent.Empty)
        NavigationBarItem(
            selected = selectedScreen is CartScreen,
            label = { Text(text = "Cart") },
            onClick = { onNavigationSelected(CartScreen) },
            icon = { ShoppingCartNavigationBarItem(productsInCart = shoppingContent.size) },
        )
    }
}

@Composable
private fun ShoppingCartNavigationBarItem(productsInCart: Int, modifier: Modifier = Modifier) {
    Box(modifier) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "Cart",
            modifier = Modifier.align(Alignment.Center)
        )
        if (productsInCart > 0) {
            Text(
                text = "$productsInCart",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(24.dp)
                    .offset(x = 20.dp, y = 12.dp.unaryMinus())
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape,
                    )
            )
        }
    }
}

private fun Navigator.resetRootIfDifferent(
    screen: Screen,
    backstack: SaveableBackStack,
) {
    if (!backstack.isAtRoot || backstack.topRecord?.screen != screen) {
        resetRoot(screen)
    }
}