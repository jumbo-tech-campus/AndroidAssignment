package dev.sierov.domain.model.category

import dev.sierov.core.parcel.Parcelable
import dev.sierov.core.parcel.Parcelize
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Parcelize
@Serializable
value class Category(val id: String) : Parcelable {
    companion object {
        val Snacks = Category(id = "SG9")
    }
}