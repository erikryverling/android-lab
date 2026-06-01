package se.yverling.lab.android.weather.ui

import android.content.res.Configuration
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import se.yverling.lab.android.data.weather.model.CurrentWeather
import se.yverling.lab.android.data.weather.model.CurrentWeather.*
import se.yverling.lab.android.design.theme.AndroidLabThemeWrapper
import se.yverling.lab.android.weather.WeatherUiState

class ScreenshotTest {
    @ExperimentalMaterialApi
    @Preview(name = "Light Mode")
    @Preview(
        name = "Dark Mode",
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true
    )
    @PreviewWrapper(AndroidLabThemeWrapper::class)
    @Composable
    private fun WeatherContentPreview() {
        DataScreen(
            uiState = WeatherUiState.Success(
                CurrentWeather(
                    temperature = 20,
                    wind = Wind(
                        speed = 10,
                        degree = 180
                    ),
                    locationName = "Location"
                )
            ),
            onRefresh = {}
        )
    }
}
