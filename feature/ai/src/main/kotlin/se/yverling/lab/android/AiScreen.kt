package se.yverling.lab.android

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import se.yverling.lab.android.AiViewModel.AiUiState.Error
import se.yverling.lab.android.AiViewModel.AiUiState.Loading
import se.yverling.lab.android.AiViewModel.AiUiState.Success
import se.yverling.lab.android.feature.ai.R
import se.yverling.lab.android.ui.CoffeeCard
import se.yverling.lab.android.ui.ErrorContent
import se.yverling.lab.android.ui.LoadingScreen
import se.yverling.lab.android.ui.RetryButton

const val AiScreenDestination = "aiScreen"

@Composable
fun AiScreen(
    modifier: Modifier = Modifier,
    viewModel: AiViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState(Loading)

    when (uiState) {
        is Loading -> LoadingScreen()

        Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ErrorContent(stringResource(R.string.unknown_error))
                    RetryButton {
                        viewModel.reload()
                    }
                }
            }
        }

        is Success -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CoffeeCard(
                    coffee = (uiState as Success).coffee,
                    isExpanded = true
                )
            }
        }
    }
}
