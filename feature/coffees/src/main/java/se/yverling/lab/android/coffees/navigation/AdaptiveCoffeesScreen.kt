@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package se.yverling.lab.android.coffees.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
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
import se.yverling.lab.android.coffees.model.Coffee
import se.yverling.lab.android.coffees.ui.CoffeeDetails
import se.yverling.lab.android.coffees.ui.CoffeeList
import se.yverling.lab.android.coffees.ui.UppercaseFab
import se.yverling.lab.android.ui.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveCoffeesScreen(
    viewModel: CoffeesViewModel = hiltViewModel(),
) {
    var selectedCoffee: Coffee? by remember { mutableStateOf(null) }
    val navigator = rememberListDetailPaneScaffoldNavigator<Nothing>()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    val uiState by viewModel.uiState.collectAsState(CoffeesUiState.Loading)


    when (uiState) {
        CoffeesUiState.Loading -> {
            LoadingScreen()
        }

        is CoffeesUiState.Success -> {
            var isUppercase by remember { mutableStateOf(false) }

            val coffees = (uiState as CoffeesUiState.Success).coffees

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Coffees") },
                    )
                },

                floatingActionButton = { UppercaseFab(isUppercase) { isUppercase = !isUppercase } }
            ) { padding ->
                ListDetailPaneScaffold(
                    directive = navigator.scaffoldDirective,
                    value = navigator.scaffoldValue,
                    listPane = {
                        AnimatedPane(Modifier.padding(padding)) {
                            CoffeeList(
                                coffees = coffees,
                                isUppercase = isUppercase,
                                showHeading = false,
                                onCoffeeClicked = { index ->
                                    selectedCoffee = coffees[index]
                                }
                            )
                        }
                    },

                    detailPane = {
                        AnimatedPane(Modifier) {
                            CoffeeDetails(
                                coffee = selectedCoffee,
                                isExpanded = true,
                            )
                        }
                    },
                )
            }
        }
    }
}
