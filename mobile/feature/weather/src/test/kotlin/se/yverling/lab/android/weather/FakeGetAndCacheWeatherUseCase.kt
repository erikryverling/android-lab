package se.yverling.lab.android.weather

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import se.yverling.lab.android.data.weather.GetAndCacheWeatherUseCase
import se.yverling.lab.android.data.weather.model.CurrentWeather

class FakeGetAndCacheWeatherUseCase(
    private var flow: Flow<CurrentWeather> = flowOf()
) : GetAndCacheWeatherUseCase {
    fun setWeather(weather: CurrentWeather) {
        flow = flowOf(weather)
    }

    fun setError(throwable: Throwable) {
        flow = flow { throw throwable }
    }

    override fun invoke(): Flow<CurrentWeather> = flow
}
