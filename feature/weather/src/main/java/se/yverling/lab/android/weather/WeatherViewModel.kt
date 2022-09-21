package se.yverling.lab.android.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import se.yverling.lab.android.data.weather.model.CurrentWeather
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val useCase: se.yverling.lab.android.data.weather.model.GetAndCacheWeatherUseCase) :
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
                    mutableUiState.value = WeatherUiState.Error(exception.message!!)
                }
                .collect { currentWeather ->
                    mutableUiState.value = WeatherUiState.Success(currentWeather)
                }
        }
    }
}

sealed class WeatherUiState(val data: Any? = null) {
    data object Loading : WeatherUiState()
    data class Error(val message: String) : WeatherUiState(message)
    data class Success(val currentWeather: CurrentWeather) : WeatherUiState(currentWeather)
}

