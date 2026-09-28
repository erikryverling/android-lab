package se.yverling.lab.android.data.weather.model

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import se.yverling.lab.android.data.weather.WeatherDataStoreRepositoryImpl
import se.yverling.lab.android.data.weather.model.CurrentWeather.Wind
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class WeatherDataStoreRepositoryTest {
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

    @Test
    fun `persistCurrentWeather() should persist successfully`() {
        val initialData = se.yverling.lab.android.CurrentWeather.getDefaultInstance()
        val fakeDataStore = FakeDataStore(initialData)
        val dataStoreRepository = WeatherDataStoreRepositoryImpl(fakeDataStore)

        runTest {
            dataStoreRepository.persistCurrentWeather(currentWeather, createdAt)

            val persisted = fakeDataStore.data.first()
            persisted.temp.shouldBe(currentWeather.temperature)
            persisted.wind.speed.shouldBe(currentWeather.wind.speed)
            persisted.wind.degree.shouldBe(currentWeather.wind.degree)
            persisted.locationName.shouldBe(currentWeather.locationName)
            persisted.createdAt.shouldBe(createdAt)
        }
    }

    @Test
    fun `fetchCurrentWeather() should fetch successfully`() {
        val dataStore = FakeDataStore(currentWeatherData)
        val dataStoreRepository = WeatherDataStoreRepositoryImpl(dataStore)

        runTest {
            val result = dataStoreRepository.fetchCurrentWeather().first()
            result.currentWeather.shouldBe(currentWeather)
            result.createdAt.shouldBe(createdAt)
        }
    }
}
