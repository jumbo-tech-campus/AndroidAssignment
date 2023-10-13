@file:OptIn(ExperimentalMultiplatform::class)

package dev.sierov.core.parcel

@OptionalExpectation
expect annotation class Parcelize()

@AllowDifferentMembersInActual
expect interface Parcelable