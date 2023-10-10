package dev.sierov.domain.model.unit

import androidx.compose.runtime.Immutable
import dev.sierov.core.parcel.Parcelable
import dev.sierov.core.parcel.Parcelize
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Parcelize
@Immutable
@Serializable
value class ItemUnit(val name: String) : Parcelable {
    companion object {
        val Kilogram = ItemUnit(name = "kg")
        val Litre = ItemUnit(name = "l")
    }
}