package dev.sierov.jumbo

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import dev.sierov.screen.StartScreen
import dev.sierov.shared.component.AndroidActivityComponent
import dev.sierov.shared.component.AndroidApplicationComponent
import dev.sierov.shared.component.create

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val applicationComponent = AndroidApplicationComponent.from(this)
        val component = AndroidActivityComponent.create(this, applicationComponent)
        setContent {
            val backstack = rememberSaveableBackStack { push(StartScreen) }
            val navigator = rememberCircuitNavigator(backstack)
            component.appContent(backstack, navigator, Modifier.fillMaxSize())
        }
    }
}

fun AndroidApplicationComponent.Companion.from(context: Context): AndroidApplicationComponent {
    val application = context.applicationContext as App
    return application.component
}