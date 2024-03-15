@file:OptIn(ExperimentalMaterial3Api::class)

package se.yverling.lab.android.coffees.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import se.yverling.lab.android.coffees.CoffeesUiState
import se.yverling.lab.android.coffees.CoffeesViewModel
import se.yverling.lab.android.coffees.model.Coffee
import se.yverling.lab.android.coffees.theme.CoffeeCardElevation
import se.yverling.lab.android.design.theme.AndroidLabTheme
import se.yverling.lab.android.design.theme.DefaultSpace
import se.yverling.lab.android.feature.coffees.R
import se.yverling.lab.android.ui.LoadingScreen

internal const val CoffeeDetailsScreenDestination = "coffeeDetailsScreen"
internal const val CoffeeDetailsScreenNavArgument = "coffeeIndex"
const val CoffeeDetailsScreenDeepLinkUri = "yverling://lab"

@Composable
internal fun CoffeeDetailsScreen(
    coffeeIndex: Int,
    onNavigateUp: () -> Unit,
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

                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CoffeeCard(coffee)
                }
            }
        }
    }
}

@Composable
fun CoffeeCard(coffee: Coffee) {
    var orientation by remember { mutableIntStateOf(Configuration.ORIENTATION_PORTRAIT) }
    val configuration = LocalConfiguration.current

    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }

    Surface(
        shape = MaterialTheme.shapes.small,
        shadowElevation = CoffeeCardElevation
    ) {
        Column(
            Modifier
                .padding(top = DefaultSpace, start = DefaultSpace, end = DefaultSpace)
        ) {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Icon(
                    Icons.Outlined.Coffee,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .height(112.dp)
                        .width(112.dp)
                        .padding(bottom = DefaultSpace)
                        .align(alignment = Alignment.CenterHorizontally),

                    contentDescription = stringResource(R.string.coffee_icon_description)
                )
            }

            InlineTitleText(title = "Origin", body = coffee.origin)
            InlineTitleText(title = "Roaster", body = coffee.roaster)
            InlineTitleText(title = "Region", body = coffee.region)
        }
    }
}

@Composable
fun InlineTitleText(title: String, body: String) {
    Text(
        modifier = Modifier.padding(bottom = DefaultSpace),
        style = MaterialTheme.typography.bodyLarge,
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("$title: ")
            }
            append(body)
        }
    )
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun CoffeeCardPreview() {
    AndroidLabTheme {
        CoffeeCard(
            Coffee(
                id = 0,
                name = "Odo Carbonic",
                roaster = "Gringo Nordic",
                origin = "Ethiopia",
                region = "Guji"
            )
        )
    }
}
