package se.yverling.lab.android.data.weather.model

import io.kotest.matchers.shouldBe
import io.mockk.Called
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetAndCacheWeatherUseCaseTest {
    @RelaxedMockK
    lateinit var networkRepositoryMock: WeatherNetworkRepository

    @RelaxedMockK
    lateinit var dataStoreRepositoryMock: WeatherDataStoreRepository

    private lateinit var getAndCacheWeatherUseCase: GetAndCacheWeatherUseCase

    private val currentWeather = CurrentWeather(
        temperature = 10,
        wind = Wind(speed = 10, degree = 10),
        locationName = "Location"
    )

    @BeforeEach
    fun setUp() {
        getAndCacheWeatherUseCase = GetAndCacheWeatherUseCase(networkRepositoryMock, dataStoreRepositoryMock)
    }

    @Test
    fun `getAndCacheWeatherUseCase should fetch current weather from network successfully`() {
        every { dataStoreRepositoryMock.fetchCurrentWeather() } returns flowOf(Pair(currentWeather, -1L))
        every { networkRepositoryMock.getCurrentWeather() } returns flowOf(currentWeather)

        runTest {
            getAndCacheWeatherUseCase.invoke().collect {
                it.shouldBe(currentWeather)
            }

            coVerify { dataStoreRepositoryMock.persistCurrentWeather(currentWeather, any()) }
        }
    }

    @Test
    fun `getAndCacheWeatherUseCase should fetch current weather from local datastore successfully`() {
        val timestamp = Clock.System.now().toEpochMilliseconds()

        every { dataStoreRepositoryMock.fetchCurrentWeather() } returns flowOf(Pair(currentWeather, timestamp))

        runTest {
            getAndCacheWeatherUseCase.invoke().collect {
                it.shouldBe(currentWeather)
            }
        }

        verify(atMost = 0, atLeast = 0) { networkRepositoryMock.getCurrentWeather() }
    }
}
