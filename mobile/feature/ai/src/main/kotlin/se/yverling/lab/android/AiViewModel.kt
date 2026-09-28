package se.yverling.lab.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import se.yverling.lab.android.ai.AiRepository
import se.yverling.lab.android.common.model.Coffee
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AiViewModel @Inject constructor(private val repository: AiRepository) : ViewModel() {
    private val mutableUiState: MutableStateFlow<AiUiState> = MutableStateFlow(AiUiState.Loading)
    internal val uiState: StateFlow<AiUiState> = mutableUiState.asStateFlow()

    init {
        load()
    }

    fun reload() {
        load()
    }

    private fun load() {
        mutableUiState.value = AiUiState.Loading

        viewModelScope.launch {
            repository.promptFlow()
                .catch { exception ->
                    Timber.e(exception, "Failed to load AI prompt")
                    mutableUiState.value = AiUiState.Error(exception.localizedMessage)
                }
                .collect { coffee ->
                    mutableUiState.value = AiUiState.Success(coffee)
                }
        }
    }

    internal sealed class AiUiState {
        data object Loading : AiUiState()
        data class Error(val message: String? = null) : AiUiState()
        data class Success(val coffee: Coffee) : AiUiState()
    }
}
