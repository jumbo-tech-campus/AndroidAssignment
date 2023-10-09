package dev.sierov.feature.cart

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dev.sierov.core.inject.ActivityScoped
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface CartComponent {

    @Provides
    @IntoSet
    @ActivityScoped
    fun bindCartPresenterFactory(factory: CartPresenterFactory): Presenter.Factory = factory

    @Provides
    @IntoSet
    @ActivityScoped
    fun bindCartUiFactory(factory: CartUiFactory): Ui.Factory = factory
}