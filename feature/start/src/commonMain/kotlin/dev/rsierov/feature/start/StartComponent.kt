package dev.rsierov.feature.start

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dev.sierov.core.inject.ActivityScoped
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface StartComponent {

    @Provides
    @IntoSet
    @ActivityScoped
    fun bindStartUiFactory(factory: StartUiFactory): Ui.Factory = factory

    @Provides
    @IntoSet
    @ActivityScoped
    fun bindStartPresenterFactory(factory: StartPresenterFactory): Presenter.Factory = factory
}