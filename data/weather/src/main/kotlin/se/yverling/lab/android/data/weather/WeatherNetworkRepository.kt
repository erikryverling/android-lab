package se.yverling.lab.android.data.weather

import io.ktor.client.call.body
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import se.yverling.lab.android.data.weather.model.CurrentWeather
import se.yverling.lab.android.data.weather.network.CurrentWeatherDto
import se.yverling.lab.android.data.weather.network.KtorWeather
import se.yverling.lab.android.data.weather.network.WeatherService
import se.yverling.lab.android.data.weather.network.toCurrentWeather
import java.util.*
import javax.inject.Inject

internal const val KTOR = "Ktor"
internal const val RETROFIT = "Retrofit"

internal const val LONGITUDE = 18.0273F
internal const val LATITUDE = 59.303F
internal const val UNITS = "metric"

class WeatherNetworkRepository(
    private val retrofitService: WeatherService,
    private val ktorWeather: KtorWeather,
    private val apiClient: String
) {
    @Inject
    constructor(retrofitService: WeatherService, ktorWeather: KtorWeather) : this(
        retrofitService,
        ktorWeather,
        apiClient = BuildConfig.API_CLIENT
    )

    fun getCurrentWeather(): Flow<CurrentWeather> = flow {

        when (apiClient) {

            KTOR -> {
                val response = ktorWeather.getCurrentWeather(
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

            RETROFIT -> {
                val response = retrofitService.getCurrentWeather(
                    apiKey = BuildConfig.API_KEY,
                    longitude = LONGITUDE,
                    latitude = LATITUDE,
                    units = UNITS,
                    languageCode = Locale.getDefault().language
                )

                if (response.isSuccessful) {
                    emit(response.body()!!.toCurrentWeather())
                } else {
                    throw java.lang.IllegalStateException("Could not parse weather response with Retrofit")
                }
            }

            else -> throw java.lang.IllegalStateException("Could not parse API_CLIENT")
        }
    }
}
