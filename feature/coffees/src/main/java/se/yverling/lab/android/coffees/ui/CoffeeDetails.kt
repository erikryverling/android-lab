package se.yverling.lab.android.coffees.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.yverling.lab.android.coffees.model.Coffee
import se.yverling.lab.android.coffees.theme.CoffeeCardElevation
import se.yverling.lab.android.coffees.theme.CoffeeCardWith
import se.yverling.lab.android.design.theme.AndroidLabTheme
import se.yverling.lab.android.design.theme.DefaultSpace
import se.yverling.lab.android.feature.coffees.R

@Composable
fun CoffeeDetails(
    coffee: Coffee?,
    isExpanded: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        if(coffee == null) {
            Text(
                text = stringResource(R.string.no_coffee_selected),
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            CoffeeCard(
                coffee = coffee,
                isExpanded = isExpanded
            )
        }
    }
}

@Composable
internal fun CoffeeCard(
    coffee: Coffee,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false
) {
    Surface(
        modifier = modifier.width(CoffeeCardWith),
        shape = MaterialTheme.shapes.small,
        shadowElevation = CoffeeCardElevation
    ) {
        Column(
            Modifier
                .padding(top = DefaultSpace, start = DefaultSpace, end = DefaultSpace)
        ) {
            if (isExpanded) {
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
                InlineTitleText(title = stringResource(R.string.coffee_name), body = coffee.name)
            }

            InlineTitleText(title = stringResource(R.string.coffee_origin), body = coffee.origin)
            InlineTitleText(title = stringResource(R.string.coffee_roaster), body = coffee.roaster)
            InlineTitleText(title = stringResource(R.string.coffee_region), body = coffee.region)
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
fun CoffeeDetailsPreview() {
    AndroidLabTheme {
        CoffeeDetails(
            Coffee(
                id = 0,
                name = "Odo Carbonic",
                roaster = "Gringo Nordic",
                origin = "Ethiopia",
                region = "Guji"
            ),
        )
    }
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
