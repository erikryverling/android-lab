package se.yverling.lab.android.data.weather.model

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import se.yverling.lab.android.data.weather.GetAndCacheWeatherUseCaseImpl
import se.yverling.lab.android.data.weather.model.CurrentWeather.Wind
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class GetAndCacheWeatherUseCaseTest {
    private val currentWeather = CurrentWeather(
        temperature = 10,
        wind = Wind(speed = 10, degree = 10),
        locationName = "Location"
    )

    @Test
    fun `getAndCacheWeatherUseCase should fetch current weather from network successfully`() {
        val networkRepository = FakeWeatherNetworkRepository(flowOf(currentWeather))
        val dataStoreRepository = FakeWeatherDataStoreRepository(
            cachedCurrentWeather = CachedCurrentWeather(currentWeather, -1L)
        )
        val getAndCacheWeatherUseCase = GetAndCacheWeatherUseCaseImpl(networkRepository, dataStoreRepository)

        runTest {
            getAndCacheWeatherUseCase.invoke().collect {
                it.shouldBe(currentWeather)
            }

            networkRepository.callCount.shouldBe(1)
            dataStoreRepository.persistCallCount.shouldBe(1)
            dataStoreRepository.persistedWeather.shouldBe(currentWeather)
        }
    }

    @Test
    fun `getAndCacheWeatherUseCase should fetch current weather from local datastore successfully`() {
        val timestamp = Clock.System.now().toEpochMilliseconds()
        val networkRepository = FakeWeatherNetworkRepository(flowOf(currentWeather))
        val dataStoreRepository = FakeWeatherDataStoreRepository(
            cachedCurrentWeather = CachedCurrentWeather(currentWeather, timestamp)
        )
        val getAndCacheWeatherUseCase = GetAndCacheWeatherUseCaseImpl(networkRepository, dataStoreRepository)

        runTest {
            getAndCacheWeatherUseCase.invoke().collect {
                it.shouldBe(currentWeather)
            }

            networkRepository.callCount.shouldBe(0)
            dataStoreRepository.persistCallCount.shouldBe(0)
        }
    }
}
