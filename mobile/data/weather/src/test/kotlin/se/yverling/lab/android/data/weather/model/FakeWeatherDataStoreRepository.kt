package se.yverling.lab.android.data.weather.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import se.yverling.lab.android.data.weather.WeatherDataStoreRepository

class FakeWeatherDataStoreRepository(
    var cachedCurrentWeather: CachedCurrentWeather? = null
) : WeatherDataStoreRepository {
    var persistCallCount = 0
        private set
    var persistedWeather: CurrentWeather? = null
        private set
    var persistedCreatedAt: Long? = null
        private set

    override suspend fun persistCurrentWeather(currentWeather: CurrentWeather, createdAt: Long) {
        persistCallCount++
        persistedWeather = currentWeather
        persistedCreatedAt = createdAt
        cachedCurrentWeather = CachedCurrentWeather(currentWeather, createdAt)
    }

    override fun fetchCurrentWeather(): Flow<CachedCurrentWeather> = flow {
        cachedCurrentWeather?.let { emit(it) }
    }
}
