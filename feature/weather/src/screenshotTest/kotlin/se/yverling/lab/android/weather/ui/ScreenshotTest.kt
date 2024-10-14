package se.yverling.lab.android.weather.ui

import android.content.res.Configuration
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import se.yverling.lab.android.data.weather.model.CurrentWeather
import se.yverling.lab.android.data.weather.model.Wind
import se.yverling.lab.android.design.theme.AndroidLabTheme
import se.yverling.lab.android.weather.WeatherUiState

class ScreenshotTest {
    @ExperimentalMaterialApi
    @Preview(name = "Light Mode")
    @Preview(
        name = "Dark Mode",
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true
    )
    @Composable
    private fun WeatherContentPreview() {
        AndroidLabTheme {
            Surface {
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
    }
}
