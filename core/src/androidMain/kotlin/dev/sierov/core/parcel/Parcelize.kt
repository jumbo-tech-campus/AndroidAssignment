package dev.sierov.core.parcel

actual typealias Parcelize = kotlinx.parcelize.Parcelize

@OptIn(ExperimentalMultiplatform::class)
@AllowDifferentMembersInActual
actual typealias Parcelable = android.os.Parcelable