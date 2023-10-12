package dev.sierov.shared.controller

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import dev.sierov.feature.root.AppContent
import dev.sierov.screen.StartScreen
import me.tatarka.inject.annotations.Inject
import platform.UIKit.UIViewController

typealias AppUiViewController = () -> UIViewController

@Suppress("FunctionName")
@Inject
fun AppUiViewController(
    appContent: AppContent,
): UIViewController = ComposeUIViewController {
    val backstack = rememberSaveableBackStack { push(StartScreen) }
    val navigator = rememberCircuitNavigator(backstack, onRootPop = { /* no-op */ })
    appContent(backstack, navigator, Modifier.fillMaxSize())
}