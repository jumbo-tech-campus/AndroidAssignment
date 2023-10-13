package dev.sierov.domain.model.price

import androidx.compose.runtime.Immutable
import dev.sierov.core.parcel.Parcelable
import dev.sierov.core.parcel.Parcelize
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Parcelize
@Immutable
@Serializable
data class Price(
    val amount: Int,
    val currency: Currency,
) : Parcelable {
    override fun toString(): String = "$currency ${amount / 100f}"
}

@JvmInline
@Parcelize
@Immutable
@Serializable
value class Currency(val name: String) : Parcelable {
    override fun toString(): String = name
}