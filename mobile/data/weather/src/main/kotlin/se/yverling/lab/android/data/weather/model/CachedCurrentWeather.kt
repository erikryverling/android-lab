package se.yverling.lab.android.data.weather.model

data class CachedCurrentWeather(
    val currentWeather: CurrentWeather,
    val createdAt: Long,
)
