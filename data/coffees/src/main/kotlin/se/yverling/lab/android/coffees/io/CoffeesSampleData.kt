package se.yverling.lab.android.coffees.io

import android.content.Context
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import se.yverling.lab.android.coffees.db.Coffee
import se.yverling.lab.android.data.coffees.R


@OptIn(ExperimentalSerializationApi::class)
object CoffeesSampleData {
    fun get(context: Context): List<Coffee> {
        return Json.decodeFromStream<Coffees>(
            context.resources.openRawResource(R.raw.coffees)
        ).toCoffeeEntityList()
    }
}
