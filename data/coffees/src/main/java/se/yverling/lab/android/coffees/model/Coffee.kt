package se.yverling.lab.android.coffees.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coffee(
    val id: Int,
    val name: String,
    val roaster: String,
    val origin: String,
    val region: String,
) : Parcelable
