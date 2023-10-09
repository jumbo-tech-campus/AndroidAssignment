package dev.sierov.feature.root

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
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

@Composable
internal fun Root(
    backstack: SaveableBackStack,
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    val rootScreen by remember(backstack) {
        derivedStateOf { backstack.last().screen }
    }

    val bottomBar = @Composable {
        BottomNavigationBar(
            onNavigationSelected = { navigator.resetRootIfDifferent(it, backstack) }
        )
    }

    Scaffold(bottomBar = bottomBar) { paddingValues ->
        NavigableCircuitContent(
            navigator = navigator,
            backstack = backstack,
            modifier = modifier
        )
    }
}

@Composable
private fun BottomNavigationBar(
    onNavigationSelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
        windowInsets = WindowInsets.navigationBars,
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { onNavigationSelected() },
            icon = { Icon(imageVector = Icons.Default.Menu, contentDescription = null) },
        )
        NavigationBarItem(
            selected = false,
            onClick = { onNavigationSelected() },
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = null) },
        )
        NavigationBarItem(
            selected = false,
            onClick = { onNavigationSelected() },
            icon = { Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null) },
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