package dev.sierov.jumbo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.Modifier
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import dev.sierov.screen.ProductsScreen
import dev.sierov.shared.component.AndroidActivityComponent
import dev.sierov.shared.component.AndroidApplicationComponent
import dev.sierov.shared.component.create

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val applicationComponent = AndroidApplicationComponent.create(application)
        val component = AndroidActivityComponent.create(this, applicationComponent)
        setContent {
            val backstack = rememberSaveableBackStack { push(ProductsScreen) }
            val navigator = rememberCircuitNavigator(backstack)
            component.appContent(backstack, navigator, Modifier)
        }
    }
}