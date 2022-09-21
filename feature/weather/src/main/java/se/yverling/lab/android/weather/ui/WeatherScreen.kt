package se.yverling.lab.android.weather.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import se.yverling.lab.android.data.weather.model.CurrentWeather
import se.yverling.lab.android.data.weather.model.Wind
import se.yverling.lab.android.design.theme.AndroidLabTheme
import se.yverling.lab.android.design.theme.DefaultSpace
import se.yverling.lab.android.design.theme.LargeSpace
import se.yverling.lab.android.feature.weather.R
import se.yverling.lab.android.ui.LoadingScreen
import se.yverling.lab.android.weather.WeatherUiState
import se.yverling.lab.android.weather.WeatherViewModel

const val WeatherScreenDestination = "weatherScreen"

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState(WeatherUiState.Loading)

    when (uiState) {
        WeatherUiState.Loading -> LoadingScreen()
        is WeatherUiState.Error -> DataScreen(modifier, uiState, onRetryButtonClicked = {
            viewModel.reload()
        })

        is WeatherUiState.Success -> DataScreen(modifier, uiState)
    }
}

@Composable
private fun DataScreen(
    modifier: Modifier = Modifier,
    uiState: WeatherUiState,
    onRetryButtonClicked: (() -> Unit)? = null
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(DefaultSpace),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState !is WeatherUiState.Success) {
            val errorMessage: String = uiState.data as String
            ErrorContent(errorMessage)
        } else {
            val currentWeather: CurrentWeather = uiState.data as CurrentWeather
            WeatherContent(currentWeather)
        }

        onRetryButtonClicked?.let { RetryButton(it) }
    }
}

@Composable
fun WeatherContent(currentWeather: CurrentWeather) {
    Text(
        currentWeather.locationName,
        Modifier.padding(bottom = DefaultSpace),
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center
    )

    Text(
        "${currentWeather.temperature} ${stringResource(R.string.temperature_suffix)}",
        Modifier.padding(bottom = DefaultSpace),
        style = MaterialTheme.typography.displayMedium,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center
    )

    Row(Modifier.padding(bottom = DefaultSpace)) {
        Text(
            "${currentWeather.wind.speed} ${stringResource(R.string.wind_speed_suffix)}",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.width(LargeSpace))

        Text(
            "${currentWeather.wind.degree} ${stringResource(R.string.wind_degree_suffix)}",
            Modifier.padding(bottom = DefaultSpace),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ErrorContent(errorMessage: String) {
    Text(
        errorMessage,
        Modifier.padding(bottom = DefaultSpace),
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.error,
        textAlign = TextAlign.Center
    )
}

@Composable
fun RetryButton(onRetryButtonClicked: () -> Unit) {
    Button(onClick = onRetryButtonClicked) {
        Text(stringResource(R.string.retry_button_title))
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun ErrorContentPreview() {
    AndroidLabTheme {
        ErrorContent("Something went wrong")
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun RetryButtonPreview() {
    AndroidLabTheme {
        RetryButton {}
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun WeatherContentPreview() {
    AndroidLabTheme {
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
            )
        )
    }
}
