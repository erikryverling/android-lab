package se.yverling.lab.android.coffees.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import se.yverling.lab.android.common.model.Coffee
import se.yverling.lab.android.design.theme.AndroidLabTheme
import se.yverling.lab.android.feature.coffees.R
import se.yverling.lab.android.ui.CoffeeCard

@Composable
internal fun CoffeeDetails(
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


@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun CoffeeDetailsPreview() {
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
