package se.yverling.lab.android.coffees

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import se.yverling.lab.android.common.model.Coffee
import javax.inject.Inject

@HiltViewModel
class CoffeesViewModel @Inject constructor(
    private val repository: CoffeesRepository
) : ViewModel() {
    private val mutableUiState: MutableStateFlow<CoffeesUiState> =
        MutableStateFlow(CoffeesUiState.Loading)

    internal var uiState: StateFlow<CoffeesUiState> = mutableUiState

    init {
        mutableUiState.value = CoffeesUiState.Loading

        viewModelScope.launch {
            loadCoffees()
        }
    }

    private suspend fun loadCoffees() {
        repository.getList().onEach {
            if (it.isEmpty()) throw IllegalStateException("List is empty")
        }.retry(1) {
            repository.prePopulateList()
            it is IllegalStateException
        }
            .collect { coffees ->
                mutableUiState.value = CoffeesUiState.Success(coffees, false)
            }
    }
}

internal sealed class CoffeesUiState {
    data object Loading : CoffeesUiState()
    data class Success(val coffees: List<Coffee>, val isUpperCase: Boolean) : CoffeesUiState()
}
