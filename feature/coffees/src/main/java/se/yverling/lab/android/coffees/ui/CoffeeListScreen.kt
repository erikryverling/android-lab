package se.yverling.lab.android.coffees.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.FontDownload
import androidx.compose.material.icons.outlined.FontDownloadOff
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import se.yverling.lab.android.coffees.CoffeesUiState
import se.yverling.lab.android.coffees.CoffeesViewModel
import se.yverling.lab.android.coffees.model.Coffee
import se.yverling.lab.android.coffees.theme.CoffeeCardElevation
import se.yverling.lab.android.design.theme.AndroidLabTheme
import se.yverling.lab.android.design.theme.DefaultSpace
import se.yverling.lab.android.design.theme.LargeSpace
import se.yverling.lab.android.design.theme.SmallSpace
import se.yverling.lab.android.feature.coffees.R
import se.yverling.lab.android.ui.LoadingScreen

internal const val CoffeeListScreenDestination = "coffeeListScreen"

@Composable
internal fun CoffeeListScreen(
    viewModel: CoffeesViewModel = hiltViewModel(),
    onNavigateToDetails: (Int) -> Unit
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
                    Modifier.padding(padding),
                    coffees,
                    isUppercase,
                    onNavigateToDetails
                )
            }
        }
    }
}

@Composable
fun UppercaseFab(isUppercase: Boolean, onClicked: () -> Unit) {
    return FloatingActionButton(
        modifier = Modifier
            .testTag("FAB"),
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        onClick = onClicked
    ) {
        Icon(
            if (isUppercase) Icons.Outlined.FontDownloadOff else Icons.Outlined.FontDownload,
            contentDescription = stringResource(R.string.uppercase_button_description)
        )
    }
}

@Composable
fun CoffeeList(
    modifier: Modifier = Modifier,
    coffees: List<Coffee>,
    isUppercase: Boolean,
    onNavigateToDetails: (Int) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(DefaultSpace)) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = LargeSpace),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                var shouldShowEasterEgg by remember { mutableStateOf(false) }
                val backgroundColor by animateColorAsState(
                    if (shouldShowEasterEgg) {
                        MaterialTheme.colorScheme.tertiary
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    }, label = "coffeeItemColor"
                )

                Text(
                    text = stringResource(R.string.coffees_title),
                    color = backgroundColor,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.clickable { shouldShowEasterEgg = !shouldShowEasterEgg }
                )

                EasterEgg(shouldShowEasterEgg)
            }
        }

        itemsIndexed(coffees) { index, coffee ->
            CoffeeItem(index, coffee, isUppercase, onNavigateToDetails)
            Spacer(modifier = Modifier.padding(SmallSpace))
        }
    }
}

@Composable
fun EasterEgg(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = slideOutHorizontally()
    ) {
        Row {
            Text(
                text = stringResource(R.string.easter_egg),
                style = MaterialTheme.typography.displayLarge
            )

            Spacer(modifier = Modifier.padding(SmallSpace))

            val infiniteTransition = rememberInfiniteTransition()
            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 5000
                        180f at 2500
                    }
                ), label = "easterEgg"
            )

            Image(
                painter = painterResource(R.drawable.ic_baseline_wb_sunny_24),
                modifier = Modifier.rotate(rotation),
                contentDescription = stringResource(R.string.sun_icon_content_description),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
            )
        }
    }
}

@Composable
fun CoffeeItem(
    coffeeIndex: Int,
    coffee: Coffee,
    isUppercase: Boolean,
    onNavigateToDetails: (Int) -> Unit
) {
    var iconIsTapped by rememberSaveable { mutableStateOf(false) }

    val iconColor by animateColorAsState(
        if (iconIsTapped) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "iconLabel"
    )

    Surface(
        shape = MaterialTheme.shapes.small,
        shadowElevation = CoffeeCardElevation,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onNavigateToDetails.invoke(coffeeIndex)
            }
    ) {
        Row(
            Modifier.padding(DefaultSpace),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                if (iconIsTapped) Icons.Filled.Coffee else Icons.Outlined.Coffee,
                tint = iconColor,
                modifier = Modifier
                    .padding(end = DefaultSpace)
                    .clickable {
                        iconIsTapped = !iconIsTapped
                    },
                contentDescription = stringResource(R.string.coffee_icon_description)
            )

            Column {
                Text(
                    modifier = Modifier.animateContentSize(),
                    text = if (isUppercase) coffee.name.uppercase() else coffee.name,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    text = "${coffee.roaster}, ${coffee.origin}",
                    maxLines = if (iconIsTapped) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun UppercaseFabPreview() {
    AndroidLabTheme {
        UppercaseFab(false) {}
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun CoffeeItemPreview() {
    AndroidLabTheme {
        CoffeeItem(
            1,
            coffee = Coffee(
                id = 1,
                name = "Odo Carbonic",
                roaster = "Gringo Nordic",
                origin = "Ethiopia",
                region = "Guji",
            ),
            isUppercase = false
        ) {}
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun EasterEggPreview() {
    AndroidLabTheme {
        EasterEgg(isVisible = true)
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun CoffeeListPreview() {
    AndroidLabTheme {
        CoffeeList(
            coffees = listOf(
                Coffee(
                    id = 1,
                    name = "Odo Carbonic",
                    roaster = "Gringo Nordic",
                    origin = "Ethiopia",
                    region = "Guji",
                ),
                Coffee(
                    id = 1,
                    name = "Uraga Carbonic",
                    roaster = "Gringo Nordic",
                    origin = "Ethiopia",
                    region = "Guji",
                )
            ),
            isUppercase = false,
            onNavigateToDetails = {}
        )
    }
}
