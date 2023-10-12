package dev.sierov.cart.local

import dev.sierov.cart.Cart
import dev.sierov.cart.ReadOnlyCart
import dev.sierov.core.inject.ApplicationScoped
import me.tatarka.inject.annotations.Provides

interface LocalCartComponent : PlatformLocalCartWiring {

    @Provides
    @ApplicationScoped
    fun localCart(cart: LocalDatastoreCart): Cart = cart

    @Provides
    @ApplicationScoped
    fun localReadOnlyCart(cart: Cart): ReadOnlyCart = cart
}