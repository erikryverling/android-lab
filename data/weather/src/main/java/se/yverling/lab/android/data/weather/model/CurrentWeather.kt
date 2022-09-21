package se.yverling.lab.android.data.weather.model

data class CurrentWeather(val temperature: Int, val wind: Wind, val locationName: String)
data class Wind(val speed: Int, val degree: Int)
