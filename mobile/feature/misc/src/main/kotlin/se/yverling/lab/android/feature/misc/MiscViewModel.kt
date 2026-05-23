package se.yverling.lab.android.feature.misc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import se.yverling.lab.android.misc.MiscRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class MiscViewModel @Inject constructor(repository: MiscRepositoryImpl) : ViewModel() {
    internal var uiState: StateFlow<MiscUiState>

    val carouselItems = repository.carouselItems

    init {
        uiState = repository.longRunningFlow().map {
            println("In map got: $it")
            if (it % 2 == 0) MiscUiState.State1 else MiscUiState.State2
            // This is an alternative to using MutableStateFlow
        }.stateIn(
            scope = viewModelScope,
            initialValue = MiscUiState.State1,
            started = SharingStarted.WhileSubscribed(3000)
        )
    }

    internal sealed class MiscUiState(val name: String) {
        data object State1 : MiscUiState("State 1")
        data object State2 : MiscUiState("State 2")
    }
}
