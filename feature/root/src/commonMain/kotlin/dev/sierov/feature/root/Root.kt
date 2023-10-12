package dev.sierov.feature.root

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.slack.circuit.backstack.SaveableBackStack
import com.slack.circuit.backstack.isAtRoot
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.Screen
import dev.sierov.screen.CartScreen
import dev.sierov.screen.ProductsScreen
import dev.sierov.screen.StartScreen

@Composable
internal fun Root(
    backstack: SaveableBackStack,
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    val rootScreen by remember(backstack) {
        derivedStateOf { checkNotNull(backstack.topRecord?.screen) { "Top record is null" } }
    }

    val bottomBar = @Composable {
        BottomNavigationBar(
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
        NavigationBarItem(
            selected = selectedScreen is CartScreen,
            label = { Text(text = "Cart") },
            onClick = { onNavigationSelected(CartScreen) },
            icon = { Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart") },
        )
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