@file:OptIn(ExperimentalMaterial3Api::class)

package se.yverling.lab.android.coffees.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import se.yverling.lab.android.coffees.CoffeesUiState
import se.yverling.lab.android.coffees.CoffeesViewModel
import se.yverling.lab.android.feature.coffees.R
import se.yverling.lab.android.ui.LoadingScreen

internal const val CoffeeDetailsScreenDestination = "coffeeDetailsScreen"
internal const val CoffeeDetailsScreenNavArgument = "coffeeIndex"
const val CoffeeDetailsScreenDeepLinkUri = "yverling://lab"

@Composable
internal fun CoffeeDetailsScreen(
    coffeeIndex: Int,
    onNavigateUp: () -> Unit = {},
    viewModel: CoffeesViewModel = hiltViewModel()
) {
    var appBarTitle by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(appBarTitle) },
                navigationIcon = {
                    IconButton(
                        content = {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back_arrow_content_description)
                            )
                        },
                        onClick = { onNavigateUp.invoke() }
                    )
                }
            )
        }
    ) { padding ->
        val uiState by viewModel.uiState.collectAsState(CoffeesUiState.Loading)

        when (uiState) {
            is CoffeesUiState.Loading -> {
                LoadingScreen()
            }

            is CoffeesUiState.Success -> {
                val coffee = (uiState as CoffeesUiState.Success).coffees[coffeeIndex]
                appBarTitle = coffee.name
                CoffeeDetails(
                    coffee = coffee,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}
