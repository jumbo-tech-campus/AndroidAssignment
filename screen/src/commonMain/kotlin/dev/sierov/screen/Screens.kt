package dev.sierov.screen

import com.slack.circuit.runtime.screen.Screen
import dev.sierov.core.parcel.Parcelable
import dev.sierov.core.parcel.Parcelize

@Parcelize
data object StartScreen : NamedScreen {
    override val name: String get() = "StartScreen"
}

@Parcelize
data object ProductsScreen : NamedScreen {
    override val name: String get() = "ProductsScreen"
}

@Parcelize
data object CartScreen : NamedScreen {
    override val name: String get() = "CartScreen"
}

interface NamedScreen : Screen, Parcelable {
    val name: String
}