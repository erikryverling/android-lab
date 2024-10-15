package se.yverling.lab.android.data.weather.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("appid") apiKey: String,
        @Query("lon") longitude: Float,
        @Query("lat") latitude: Float,
        @Query("units") units: String,
        @Query("lang") languageCode: String
    ): Response<CurrentWeatherDto>
}
