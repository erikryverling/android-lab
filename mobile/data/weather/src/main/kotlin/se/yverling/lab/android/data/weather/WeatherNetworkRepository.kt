package se.yverling.lab.android.data.weather

import io.ktor.client.call.body
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import se.yverling.lab.android.data.weather.model.CurrentWeather
import se.yverling.lab.android.data.weather.network.CurrentWeatherDto
import se.yverling.lab.android.data.weather.network.WeatherApi
import se.yverling.lab.android.data.weather.network.toCurrentWeather
import java.util.*
import javax.inject.Inject

internal const val LONGITUDE = 18.0273F
internal const val LATITUDE = 59.303F
internal const val UNITS = "metric"

class WeatherNetworkRepository @Inject constructor(private val weatherApi: WeatherApi) {
    fun getCurrentWeather(): Flow<CurrentWeather> = flow {
        val response = weatherApi.getCurrentWeather(
            apiKey = BuildConfig.API_KEY,
            longitude = LONGITUDE,
            latitude = LATITUDE,
            units = UNITS,
            languageCode = Locale.getDefault().language
        )

        if (response.status.isSuccess()) {
            emit(response.body<CurrentWeatherDto>().toCurrentWeather())
        } else {
            throw java.lang.IllegalStateException("Could not parse weather response with Ktor")
        }
    }
}
