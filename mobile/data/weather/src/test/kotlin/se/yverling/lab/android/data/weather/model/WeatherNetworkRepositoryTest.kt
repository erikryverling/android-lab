package se.yverling.lab.android.data.weather.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import se.yverling.lab.android.data.weather.WeatherNetworkRepositoryImpl
import se.yverling.lab.android.data.weather.network.WeatherApi

class WeatherNetworkRepositoryTest {
    @Test
    fun `getcurrentweather should get current weather successfully`() {
        val json = """
            {
                "main": { "temp": 20.0 },
                "wind": { "speed": 5.0, "deg": 90 },
                "name": "Årstaberg"
            }
        """.trimIndent()

        val mockEngine = MockEngine {
            respond(
                content = ByteReadChannel(json),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val weatherApi = WeatherApi(client)
        val networkRepository = WeatherNetworkRepositoryImpl(weatherApi)

        runTest {
            networkRepository.getCurrentWeather().collect {
                it.shouldBe(model)
            }
        }
    }

    @Test
    fun `getcurrentweather should throw on error response`() {
        val mockEngine = MockEngine {
            respond(
                content = ByteReadChannel("Error"),
                status = HttpStatusCode.InternalServerError
            )
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val weatherApi = WeatherApi(client)
        val networkRepository = WeatherNetworkRepositoryImpl(weatherApi)

        runTest {
            shouldThrow<IllegalStateException> {
                networkRepository.getCurrentWeather().collect()
            }
        }
    }
}

private val model = CurrentWeather(
    temperature = 20,
    wind = CurrentWeather.Wind(speed = 5, degree = 90),
    locationName = "Årstaberg"
)
