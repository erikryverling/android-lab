package se.yverling.lab.android.coffees.io

import kotlinx.serialization.Serializable

@Serializable
internal data class Coffees(
    val coffees: List<Coffee>,
)

@Serializable
data class Coffee(
    val name: String,
    val roaster: String,
    val origin: String,
    val region: String,
)

internal fun Coffees.toCoffeeEntityList(): List<se.yverling.lab.android.coffees.db.Coffee> {
    return coffees.map {
        se.yverling.lab.android.coffees.db.Coffee(
            name = it.name,
            roaster = it.roaster,
            origin = it.origin,
            region = it.region,
        )
    }
}
