package se.yverling.lab.android.data.weather.model

import io.kotest.matchers.shouldBe
import io.mockk.Called
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetAndCacheWeatherUseCaseTest {
    @get:Rule
    val mockkRule = MockKRule(this)

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

    @Before
    fun setUp() {
        getAndCacheWeatherUseCase = GetAndCacheWeatherUseCase(networkRepositoryMock, dataStoreRepositoryMock)
    }

    @Test
    fun `Should fetch current weather from network successfully`() {
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
    fun `Should fetch current weather from local datastore successfully`() {
        val timestamp = Clock.System.now().toEpochMilliseconds()

        every { dataStoreRepositoryMock.fetchCurrentWeather() } returns flowOf(Pair(currentWeather, timestamp))

        runTest {
            getAndCacheWeatherUseCase.invoke().collect {
                it.shouldBe(currentWeather)
            }
        }

        verify { networkRepositoryMock.getCurrentWeather() wasNot Called }
    }
}
