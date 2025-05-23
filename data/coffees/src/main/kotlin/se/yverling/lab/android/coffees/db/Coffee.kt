package se.yverling.lab.android.coffees.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Coffee(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    val name: String,
    val roaster: String,
    val origin: String,
    val region: String,
)

internal fun Coffee.toCoffeeModel(): se.yverling.lab.android.common.model.Coffee =
    se.yverling.lab.android.common.model.Coffee(
        id = uid!!,
        name = name,
        roaster = roaster,
        origin = origin,
        region = region,
    )
