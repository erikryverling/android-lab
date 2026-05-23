package se.yverling.lab.android.data.weather.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class WeatherApi @Inject constructor(private val client: HttpClient) {
    suspend fun getCurrentWeather(
        apiKey: String,
        longitude: Float,
        latitude: Float,
        units: String,
        languageCode: String
    ): HttpResponse {
        val response = client.get("weather") {
            url {
                parameters.append("appid", apiKey)
                parameters.append("lat", latitude.toString())
                parameters.append("lon", longitude.toString())
                parameters.append("units", units)
                parameters.append("lang", languageCode)
            }
        }
        return response
    }
}
