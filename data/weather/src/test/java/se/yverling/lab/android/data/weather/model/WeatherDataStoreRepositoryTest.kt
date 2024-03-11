package se.yverling.lab.android.data.weather.model

import android.content.Context
import androidx.datastore.core.DataStore
import io.kotest.matchers.shouldBe
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class WeatherDataStoreRepositoryTest {
    @MockK
    lateinit var contextMock: Context

    @RelaxedMockK
    lateinit var dataStoreMock: DataStore<se.yverling.lab.android.CurrentWeather>

    private lateinit var dataStoreRepository: WeatherDataStoreRepository

    private val createdAt = Clock.System.now().toEpochMilliseconds()

    private val currentWeatherData = se.yverling.lab.android.CurrentWeather.newBuilder()
        .setTemp(10)
        .setWind(
            se.yverling.lab.android.Wind.newBuilder()
                .setSpeed(10)
                .setDegree(10)
        )
        .setLocationName("Location")
        .setCreatedAt(createdAt)
        .build()

    private val currentWeather = CurrentWeather(
        temperature = 10,
        wind = Wind(speed = 10, degree = 10),
        locationName = "Location"
    )

    @BeforeEach
    fun setUp() {
        dataStoreRepository = WeatherDataStoreRepository(contextMock)

        mockkStatic(Context::currentWeatherDataStore)

        every { contextMock.currentWeatherDataStore } returns dataStoreMock
    }

    @Test
    fun `persistCurrentWeather() should persist successfully`() {
        runTest {
            dataStoreRepository.persistCurrentWeather(currentWeather, createdAt)

            /* TODO We should be verifying that currentWeather is being used when persisting the datastore
             but that turned out to be very tricky. Let's save it for a rainy day :) */
            coVerify { dataStoreMock.updateData(any()) }
        }
    }

    @Test
    fun `fetchCurrentWeather() should fetch successfully`() {
        every { dataStoreMock.data } returns flowOf(currentWeatherData)

        runTest {
            dataStoreRepository.fetchCurrentWeather().collect {
                it.first.shouldBe(currentWeather)
                it.second.shouldBe(createdAt)
            }
        }
    }
}
