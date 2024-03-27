package se.yverling.lab.android.coffees.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import se.yverling.lab.android.coffees.CoffeesUiState
import se.yverling.lab.android.coffees.CoffeesViewModel
import se.yverling.lab.android.ui.LoadingScreen

internal const val CoffeeListScreenDestination = "coffeeListScreen"

@Composable
internal fun CoffeeListScreen(
    viewModel: CoffeesViewModel = hiltViewModel(),
    onCoffeeClicked: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState(CoffeesUiState.Loading)

    when (uiState) {
        is CoffeesUiState.Loading -> {
            LoadingScreen()
        }

        is CoffeesUiState.Success -> {
            var isUppercase by remember { mutableStateOf(false) }

            val coffees = (uiState as CoffeesUiState.Success).coffees

            Scaffold(
                floatingActionButton = { UppercaseFab(isUppercase) { isUppercase = !isUppercase } }
            ) { padding ->
                CoffeeList(
                    modifier = Modifier.padding(padding),
                    coffees = coffees,
                    isUppercase = isUppercase,
                    showHeading = true,
                    onCoffeeClicked = onCoffeeClicked
                )
            }
        }
    }
}
