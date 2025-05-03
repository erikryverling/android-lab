package se.yverling.lab.android.data.weather.model

import io.kotest.matchers.shouldBe
import io.ktor.client.call.HttpClientCall
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response
import se.yverling.lab.android.data.weather.BuildConfig
import se.yverling.lab.android.data.weather.KTOR
import se.yverling.lab.android.data.weather.LATITUDE
import se.yverling.lab.android.data.weather.LONGITUDE
import se.yverling.lab.android.data.weather.RETROFIT
import se.yverling.lab.android.data.weather.UNITS
import se.yverling.lab.android.data.weather.WeatherNetworkRepository
import se.yverling.lab.android.data.weather.network.CurrentWeatherDto
import se.yverling.lab.android.data.weather.network.CurrentWeatherDto.*
import se.yverling.lab.android.data.weather.network.KtorWeather
import se.yverling.lab.android.data.weather.network.WeatherService

@ExtendWith(MockKExtension::class)
class WeatherNetworkRepositoryTest {
    @MockK
    lateinit var serviceMock: WeatherService

    @MockK
    lateinit var ktorWeatherMock: KtorWeather

    @MockK
    lateinit var httpResponseMock: HttpResponse

    @MockK
    lateinit var httpClientCallMock: HttpClientCall

    private lateinit var networkRepository: WeatherNetworkRepository

    @Test
    fun `getCurrentWeather() should get current weather successfully with Retrofit`() {
        networkRepository = WeatherNetworkRepository(serviceMock, ktorWeatherMock, RETROFIT)

        val response = Response.success(dto)

        runTest {
            coEvery {
                serviceMock.getCurrentWeather(
                    apiKey = BuildConfig.API_KEY,
                    longitude = LONGITUDE,
                    latitude = LATITUDE,
                    units = UNITS,
                    languageCode = any()
                )
            } returns response

            networkRepository.getCurrentWeather().collect {
                it.shouldBe(model)
            }
        }
    }

    @Test
    fun `getCurrentWeather() should get current weather successfully with Ktor`() {
        networkRepository = WeatherNetworkRepository(serviceMock, ktorWeatherMock, KTOR)

        every { httpResponseMock.status } returns HttpStatusCode(200, "Success")
        every { httpResponseMock.call } returns httpClientCallMock
        coEvery { httpClientCallMock.bodyNullable(any()) } returns dto

        runTest {
            coEvery {
                ktorWeatherMock.getCurrentWeather(
                    apiKey = BuildConfig.API_KEY,
                    longitude = LONGITUDE,
                    latitude = LATITUDE,
                    units = UNITS,
                    languageCode = any()
                )
            } returns httpResponseMock

            networkRepository.getCurrentWeather().collect {
                it.shouldBe(model)
            }
        }
    }
}

private val dto = CurrentWeatherDto(
    main = Main(temp = 20f),
    wind = Wind(speed = 5f, deg = 90),
    name = "Årstaberg"
)

private val model = CurrentWeather(
    temperature = 20,
    wind = CurrentWeather.Wind(speed = 5, degree = 90),
    locationName = "Årstaberg"
)
