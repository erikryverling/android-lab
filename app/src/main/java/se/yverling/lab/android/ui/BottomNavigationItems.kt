package se.yverling.lab.android.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.LocalMovies
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.HomeRepairService
import androidx.compose.material.icons.outlined.LocalMovies
import androidx.compose.material.icons.outlined.WbCloudy
import androidx.compose.ui.graphics.vector.ImageVector
import se.yverling.lab.android.R
import se.yverling.lab.android.coffees.navigation.CoffeesRoute
import se.yverling.lab.android.feature.animation.AnimationScreenDestination
import se.yverling.lab.android.feature.misc.MiscScreenDestination
import se.yverling.lab.android.weather.ui.WeatherScreenDestination

sealed class BottomNavigationItems(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    @StringRes val iconContentDescription: Int
) {
    data object Coffees :
        BottomNavigationItems(
            route = CoffeesRoute,
            label = R.string.coffees_item_label,
            icon = Icons.Outlined.Coffee,
            selectedIcon = Icons.Filled.Coffee,
            iconContentDescription = R.string.coffees_icon_content_description
        )

    data object Weather : BottomNavigationItems(
        route = WeatherScreenDestination,
        label = R.string.weather_item_label,
        icon = Icons.Outlined.WbCloudy,
        selectedIcon = Icons.Filled.WbCloudy,
        iconContentDescription = R.string.weather_icon_content_description
    )

    data object Animation : BottomNavigationItems(
        route = AnimationScreenDestination,
        label = R.string.animation_item_label,
        icon = Icons.Outlined.LocalMovies,
        selectedIcon = Icons.Filled.LocalMovies,
        iconContentDescription = R.string.weather_icon_content_description
    )

    data object Misc : BottomNavigationItems(
        route = MiscScreenDestination,
        label = R.string.misc_item_label,
        icon = Icons.Outlined.HomeRepairService,
        selectedIcon = Icons.Filled.HomeRepairService,
        iconContentDescription = R.string.misc_icon_content_description
    )
}
