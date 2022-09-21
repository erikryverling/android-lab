package se.yverling.lab.android.weather

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import se.yverling.lab.android.data.weather.model.CurrentWeather
import se.yverling.lab.android.data.weather.model.GetAndCacheWeatherUseCase
import se.yverling.lab.android.data.weather.model.Wind
import se.yverling.lab.android.test.MainDispatcherRule

class WeatherViewModelTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val mainRule = MainDispatcherRule()

    @RelaxedMockK
    lateinit var useCaseMock: GetAndCacheWeatherUseCase

    private lateinit var viewModel: WeatherViewModel

    @Test
    fun `Should load data successfully`() {
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
    fun `Should handle error successfully`() {
        val errorMessage = "error"

        every { useCaseMock.invoke() } returns flow { throw IllegalStateException(errorMessage) }

        viewModel = WeatherViewModel(useCaseMock)

        runTest {
            viewModel.uiState.test {
                val errorItem = awaitItem()
                errorItem.shouldBeTypeOf<WeatherUiState.Error>()
                errorItem.message.shouldBe(errorMessage)

                cancelAndConsumeRemainingEvents()
            }
        }
    }
}
