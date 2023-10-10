package dev.sierov.domain.model.price

import dev.sierov.core.parcel.Parcelable
import dev.sierov.core.parcel.Parcelize
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Parcelize
@Serializable
data class Price(
    val amount: Int,
    val currency: Currency,
) : Parcelable

@JvmInline
@Parcelize
@Serializable
value class Currency(val name: String) : Parcelable