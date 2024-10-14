package se.yverling.lab.android.weather

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import se.yverling.lab.android.data.weather.GetAndCacheWeatherUseCase
import se.yverling.lab.android.data.weather.model.CurrentWeather
import se.yverling.lab.android.feature.weather.R
import java.nio.channels.UnresolvedAddressException
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val useCase: GetAndCacheWeatherUseCase
) :
    ViewModel() {
    private val mutableUiState: MutableStateFlow<WeatherUiState> =
        MutableStateFlow(WeatherUiState.Loading)

    internal var uiState: StateFlow<WeatherUiState> = mutableUiState

    init {
        load()
    }

    fun reload() {
        load()
    }

    private fun load() {
        mutableUiState.value = WeatherUiState.Loading

        viewModelScope.launch {
            useCase()
                .catch { exception ->
                    val errorMessage = if (exception is UnresolvedAddressException) {
                        R.string.no_network_connection
                    } else {
                        R.string.unknown_error
                    }
                    mutableUiState.value = WeatherUiState.Error(errorMessage)
                }
                .collect { currentWeather ->
                    mutableUiState.value = WeatherUiState.Success(currentWeather)
                }
        }
    }
}

internal sealed class WeatherUiState(val data: Any? = null) {
    data object Loading : WeatherUiState()
    data class Error(@StringRes val message: Int) : WeatherUiState(message)
    data class Success(val currentWeather: CurrentWeather) : WeatherUiState(currentWeather)
}

