package dev.sierov.feature.products

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dev.sierov.core.inject.ActivityScoped
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface ProductsComponent {
    @Provides
    @IntoSet
    @ActivityScoped
    fun bindProductsPresenterFactory(factory: ProductsPresenterFactory): Presenter.Factory = factory

    @Provides
    @IntoSet
    @ActivityScoped
    fun bindProductsUiFactory(factory: ProductsUiFactory): Ui.Factory = factory
}