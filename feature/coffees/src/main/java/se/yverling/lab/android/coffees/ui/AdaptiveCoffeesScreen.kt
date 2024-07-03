package se.yverling.lab.android.coffees.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue.Companion.Expanded
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue.Companion.Hidden
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import se.yverling.lab.android.coffees.CoffeesUiState
import se.yverling.lab.android.coffees.CoffeesViewModel
import se.yverling.lab.android.coffees.model.Coffee
import se.yverling.lab.android.feature.coffees.R
import se.yverling.lab.android.ui.LoadingScreen

const val CoffeesRoute = "coffees"

@ExperimentalMaterial3AdaptiveApi
@ExperimentalMaterial3Api
@Composable
fun AdaptiveCoffeesScreen(
    viewModel: CoffeesViewModel = hiltViewModel(),
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Coffee>()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    val uiState by viewModel.uiState.collectAsState(CoffeesUiState.Loading)

    when (uiState) {
        CoffeesUiState.Loading -> {
            LoadingScreen()
        }

        is CoffeesUiState.Success -> {
            var isUppercase by rememberSaveable { mutableStateOf(false) }

            val coffees = (uiState as CoffeesUiState.Success).coffees

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            if (navigator.scaffoldValue.secondary == Expanded) {
                                Text("Coffees")
                            }
                        },

                        navigationIcon = {
                            if (navigator.scaffoldValue.secondary == Hidden)
                                IconButton(
                                    content = {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = stringResource(R.string.back_arrow_content_description)
                                        )
                                    },
                                    onClick = {
                                        navigator.navigateTo(ListDetailPaneScaffoldRole.List)
                                    }
                                )
                        }
                    )
                },

                floatingActionButton = {
                    if (navigator.scaffoldValue.secondary == Expanded) {
                        UppercaseFab(isUppercase) { isUppercase = !isUppercase }
                    }
                }
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
                                onCoffeeClicked = { coffee ->
                                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, coffee)
                                },
                            )
                        }
                    },

                    detailPane = {
                        AnimatedPane(Modifier) {
                            navigator.currentDestination?.content?.let {
                                CoffeeDetails(coffee = it, isExpanded = true)
                            }
                        }
                    },
                )
            }
        }
    }
}
