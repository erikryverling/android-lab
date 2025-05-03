package se.yverling.lab.android.data.weather.network

import kotlinx.serialization.Serializable
import se.yverling.lab.android.data.weather.model.CurrentWeather
import kotlin.math.roundToInt

@Serializable
data class CurrentWeatherDto(val main: Main, val wind: Wind, val name: String) {
    @Serializable
    data class Main(val temp: Float)

    @Serializable
    data class Wind(val speed: Float, val deg: Int)

}


fun CurrentWeatherDto.toCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        temperature = this.main.temp.roundToInt(),
        wind = se.yverling.lab.android.data.weather.model.Wind(
            speed = this.wind.speed.roundToInt(),
            degree = this.wind.deg
        ),
        locationName = this.name
    )
}

