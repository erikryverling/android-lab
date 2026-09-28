package se.yverling.lab.android.data.weather

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import se.yverling.lab.android.data.weather.datastore.CurrentWeatherSerializer
import se.yverling.lab.android.data.weather.datastore.DATASTORE_FILE_NAME
import se.yverling.lab.android.data.weather.model.CachedCurrentWeather
import se.yverling.lab.android.data.weather.model.CurrentWeather
import se.yverling.lab.android.data.weather.model.CurrentWeather.Wind
import javax.inject.Inject

interface WeatherDataStoreRepository {
    suspend fun persistCurrentWeather(currentWeather: CurrentWeather, createdAt: Long)
    fun fetchCurrentWeather(): Flow<CachedCurrentWeather>
}

class WeatherDataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<se.yverling.lab.android.CurrentWeather>
) : WeatherDataStoreRepository {
    constructor(@ApplicationContext context: Context) : this(context.currentWeatherDataStore)

    override suspend fun persistCurrentWeather(currentWeather: CurrentWeather, createdAt: Long) {
        dataStore.updateData {
            it.toBuilder()
                .setTemp(currentWeather.temperature)
                .setWind(
                    se.yverling.lab.android.Wind.newBuilder()
                        .setSpeed(currentWeather.wind.speed)
                        .setDegree(currentWeather.wind.degree)
                )
                .setLocationName(currentWeather.locationName)
                .setCreatedAt(createdAt)
                .build()
        }
    }

    override fun fetchCurrentWeather(): Flow<CachedCurrentWeather> =
        dataStore.data.map {
            CachedCurrentWeather(
                currentWeather = CurrentWeather(it.temp, Wind(it.wind.speed, it.wind.degree), it.locationName),
                createdAt = it.createdAt
            )
        }
}

val Context.currentWeatherDataStore: DataStore<se.yverling.lab.android.CurrentWeather> by dataStore(
    fileName = DATASTORE_FILE_NAME,
    serializer = CurrentWeatherSerializer
)
