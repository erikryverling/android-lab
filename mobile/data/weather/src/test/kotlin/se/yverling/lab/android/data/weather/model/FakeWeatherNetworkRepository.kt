package se.yverling.lab.android.data.weather.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import se.yverling.lab.android.data.weather.WeatherNetworkRepository

class FakeWeatherNetworkRepository(
    private var weatherFlow: Flow<CurrentWeather> = flowOf()
) : WeatherNetworkRepository {
    var callCount = 0
        private set

    fun setWeatherFlow(flow: Flow<CurrentWeather>) {
        weatherFlow = flow
    }

    override fun getCurrentWeather(): Flow<CurrentWeather> {
        callCount++
        return weatherFlow
    }
}
