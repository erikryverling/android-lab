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
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import se.yverling.lab.android.coffees.theme.CoffeeItemElevation
import se.yverling.lab.android.common.model.Coffee
import se.yverling.lab.android.design.theme.AndroidLabTheme
import se.yverling.lab.android.design.theme.DefaultSpace
import se.yverling.lab.android.design.theme.LargeSpace
import se.yverling.lab.android.design.theme.SmallSpace
import se.yverling.lab.android.feature.coffees.R


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
    coffees: List<Coffee>,
    isUppercase: Boolean,
    showHeading: Boolean,
    modifier: Modifier = Modifier,
    onCoffeeClicked: (Coffee) -> Unit,
) {
    var selectedCoffeeIndex by remember { mutableIntStateOf(-1) }

    LazyColumn(modifier = Modifier.padding(DefaultSpace)) {
        if (showHeading) {
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
        }

        itemsIndexed(coffees) { index, coffee ->
            CoffeeItem(
                index = index,
                coffee = coffee,
                isUppercase = isUppercase,
                isSelected = index == selectedCoffeeIndex
            ) { coffeeIndex ->
                selectedCoffeeIndex = coffeeIndex
                onCoffeeClicked(coffee)
            }

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
    index: Int,
    coffee: Coffee,
    isUppercase: Boolean,
    isSelected: Boolean,
    onCoffeeClicked: (Int) -> Unit
) {
    var iconIsTapped by rememberSaveable { mutableStateOf(false) }

    val iconColor by animateColorAsState(
        if (iconIsTapped) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "iconLabel"
    )

    Surface(
        shape = MaterialTheme.shapes.small,
        color = if (isSelected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface,
        shadowElevation = CoffeeItemElevation,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCoffeeClicked.invoke(index)
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
        Surface {
            CoffeeItem(
                1,
                coffee = Coffee(
                    id = 1,
                    name = "Odo Carbonic",
                    roaster = "Gringo Nordic",
                    origin = "Ethiopia",
                    region = "Guji",
                ),
                isUppercase = false,
                isSelected = false
            ) {}
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
fun EasterEggPreview() {
    AndroidLabTheme {
        Surface {
            EasterEgg(isVisible = true)
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
fun CoffeeListPreview() {
    AndroidLabTheme {
        Surface {
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
                showHeading = true,
                onCoffeeClicked = {}
            )
        }
    }
}
