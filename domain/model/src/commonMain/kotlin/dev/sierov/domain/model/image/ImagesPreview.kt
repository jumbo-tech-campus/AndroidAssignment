package dev.sierov.domain.model.image

import androidx.compose.runtime.Immutable
import dev.sierov.core.parcel.Parcelable
import dev.sierov.core.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Immutable
@Serializable
data class ImagesPreview(
    @SerialName("primaryView") private val primaryView: List<Image>,
) : Parcelable {
    val primary = primaryView.sorted()
    fun largestOrNull() = primary.lastOrNull()
    fun smallestOrNull() = primary.firstOrNull()
}

@Parcelize
@Immutable
@Serializable
data class Image(
    @SerialName("url") val url: String,
    @SerialName("width") val width: Int,
    @SerialName("height") val height: Int,
) : Parcelable, Comparable<Image> {
    override fun compareTo(other: Image): Int =
        // we want to compare image by its max dimension size
        maxOf(this.width, this.height) - maxOf(other.width, other.height)
}
