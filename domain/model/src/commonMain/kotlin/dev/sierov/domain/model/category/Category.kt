package dev.sierov.domain.model.category

import androidx.compose.runtime.Immutable
import dev.sierov.core.parcel.Parcelable
import dev.sierov.core.parcel.Parcelize
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Parcelize
@Immutable
@Serializable
value class Category(val id: String) : Parcelable {
    companion object {
        val Snacks = Category(id = "SG9")
    }
}