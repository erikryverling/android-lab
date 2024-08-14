package se.yverling.lab.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import se.yverling.lab.android.common.model.Coffee
import se.yverling.lab.android.ai.AiRepository
import javax.inject.Inject

@HiltViewModel
class AiViewModel @Inject constructor(private val repository: AiRepository) : ViewModel() {
    private val mutableUiState: MutableStateFlow<AiUiState> = MutableStateFlow(AiUiState.Loading)
    var uiState: StateFlow<AiUiState> = mutableUiState

    init {
        load()
    }

    fun reload() {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            repository.promptFlow()
                .catch {
                    mutableUiState.value = AiUiState.Error
                }
                .collect { coffee ->
                    mutableUiState.value = AiUiState.Success(coffee)
                }
        }
    }

    sealed class AiUiState {
        data object Loading : AiUiState()
        data object Error : AiUiState()
        data class Success(val coffee: Coffee) : AiUiState()
    }
}
