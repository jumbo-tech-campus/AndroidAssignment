package dev.sierov.shared.component

import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dev.rsierov.feature.start.StartComponent
import dev.sierov.core.inject.ActivityScoped
import dev.sierov.feature.cart.CartComponent
import dev.sierov.feature.products.ProductsComponent
import me.tatarka.inject.annotations.Provides

interface FeaturesComponent :
    StartComponent,
    ProductsComponent,
    CartComponent {

    @Provides
    @ActivityScoped
    fun provideCircuit(
        uiFactories: Set<Ui.Factory>,
        presenterFactories: Set<Presenter.Factory>,
    ): Circuit = Circuit.Builder()
        .addUiFactories(uiFactories)
        .addPresenterFactories(presenterFactories)
        .build()
}