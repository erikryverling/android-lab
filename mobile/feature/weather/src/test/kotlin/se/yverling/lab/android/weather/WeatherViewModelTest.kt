package se.yverling.lab.android.weather

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import se.yverling.lab.android.data.weather.model.CurrentWeather
import se.yverling.lab.android.data.weather.model.CurrentWeather.Wind
import se.yverling.lab.android.test.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class WeatherViewModelTest {
    private lateinit var viewModel: WeatherViewModel

    @Test
    fun `uiState should emit data successfully`() {
        val currentWeather = CurrentWeather(
            temperature = 10,
            wind = Wind(speed = 10, degree = 10),
            locationName = "Location"
        )

        val fakeUseCase = FakeGetAndCacheWeatherUseCase(flowOf(currentWeather))
        viewModel = WeatherViewModel(fakeUseCase)

        runTest {
            viewModel.uiState.test {
                val successItem = awaitItem()
                successItem.shouldBeTypeOf<WeatherUiState.Success>()
                successItem.currentWeather.shouldBe(currentWeather)

                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `uiState should emit error on exception`() {
        val fakeUseCase = FakeGetAndCacheWeatherUseCase(flow { throw IllegalStateException() })
        viewModel = WeatherViewModel(fakeUseCase)

        runTest {
            viewModel.uiState.test {
                val errorItem = awaitItem()
                errorItem.shouldBeTypeOf<WeatherUiState.Error>()

                cancelAndConsumeRemainingEvents()
            }
        }
    }
}
