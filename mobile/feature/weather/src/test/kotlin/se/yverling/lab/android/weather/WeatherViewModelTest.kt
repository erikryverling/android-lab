package se.yverling.lab.android.weather

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import se.yverling.lab.android.data.weather.GetAndCacheWeatherUseCase
import se.yverling.lab.android.data.weather.model.CurrentWeather
import se.yverling.lab.android.data.weather.model.CurrentWeather.Wind
import se.yverling.lab.android.test.MainDispatcherExtension

@ExtendWith(MockKExtension::class)
@ExtendWith(MainDispatcherExtension::class)
class WeatherViewModelTest {
    @RelaxedMockK
    lateinit var useCaseMock: GetAndCacheWeatherUseCase

    private lateinit var viewModel: WeatherViewModel

    @Test
    fun `uiState should emit data successfully`() {
        val currentWeather = CurrentWeather(
            temperature = 10,
            wind = Wind(speed = 10, degree = 10),
            locationName = "Location"
        )

        currentWeather.shouldBeTypeOf<CurrentWeather>()

        every { useCaseMock.invoke() } returns flowOf(currentWeather)

        viewModel = WeatherViewModel(useCaseMock)

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
    fun `uiState emit error successfully`() {
        every { useCaseMock.invoke() } returns flow { throw IllegalStateException() }

        viewModel = WeatherViewModel(useCaseMock)

        runTest {
            viewModel.uiState.test {
                val errorItem = awaitItem()
                errorItem.shouldBeTypeOf<WeatherUiState.Error>()

                cancelAndConsumeRemainingEvents()
            }
        }
    }
}
