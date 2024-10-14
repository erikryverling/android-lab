package se.yverling.lab.android.data.weather

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import se.yverling.lab.android.data.weather.datastore.CurrentWeatherSerializer
import se.yverling.lab.android.data.weather.datastore.DATASTORE_FILE_NAME
import se.yverling.lab.android.data.weather.model.CurrentWeather
import se.yverling.lab.android.data.weather.model.Wind
import javax.inject.Inject

class WeatherDataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {
    suspend fun persistCurrentWeather(currentWeather: CurrentWeather, createdAt: Long) {
        context.currentWeatherDataStore.updateData {
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

    fun fetchCurrentWeather(): Flow<Pair<CurrentWeather, Long>> =
        context.currentWeatherDataStore.data.map {
            Pair(
                CurrentWeather(it.temp, Wind(it.wind.speed, it.wind.degree), it.locationName),
                it.createdAt
            )
        }
}

val Context.currentWeatherDataStore: DataStore<se.yverling.lab.android.CurrentWeather> by dataStore(
    fileName = DATASTORE_FILE_NAME,
    serializer = CurrentWeatherSerializer
)
