package se.yverling.lab.android.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Coffee(
    val id: Int = -1,
    val name: String,
    val roaster: String,
    val origin: String,
    val region: String,
) : Parcelable
