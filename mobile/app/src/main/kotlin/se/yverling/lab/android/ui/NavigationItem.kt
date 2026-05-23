package se.yverling.lab.android.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.Diamond
import androidx.compose.material.icons.outlined.HomeRepairService
import androidx.compose.material.icons.outlined.WbCloudy
import androidx.compose.ui.graphics.vector.ImageVector
import se.yverling.lab.android.AiScreenDestination
import se.yverling.lab.android.R
import se.yverling.lab.android.coffees.ui.CoffeesRoute
import se.yverling.lab.android.feature.misc.MiscScreenDestination
import se.yverling.lab.android.weather.ui.WeatherScreenRoute

sealed class NavigationItem(
    val route: String,
    @param:StringRes val label: Int,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    @param:StringRes val iconContentDescription: Int
) {
    data object Coffees : NavigationItem(
        route = CoffeesRoute,
        label = R.string.coffees_item_label,
        icon = Icons.Outlined.Coffee,
        selectedIcon = Icons.Filled.Coffee,
        iconContentDescription = R.string.coffees_icon_content_description
    )

    data object Weather : NavigationItem(
        route = WeatherScreenRoute,
        label = R.string.weather_item_label,
        icon = Icons.Outlined.WbCloudy,
        selectedIcon = Icons.Filled.WbCloudy,
        iconContentDescription = R.string.weather_icon_content_description
    )

    data object Ai : NavigationItem(
        route = AiScreenDestination,
        label = R.string.ai_item_label,
        icon = Icons.Outlined.Diamond,
        selectedIcon = Icons.Filled.Diamond,
        iconContentDescription = R.string.ai_icon_content_description
    )

    data object Misc : NavigationItem(
        route = MiscScreenDestination,
        label = R.string.misc_item_label,
        icon = Icons.Outlined.HomeRepairService,
        selectedIcon = Icons.Filled.HomeRepairService,
        iconContentDescription = R.string.misc_icon_content_description
    )
}
