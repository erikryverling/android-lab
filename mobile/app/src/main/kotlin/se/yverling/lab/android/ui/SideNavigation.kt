package se.yverling.lab.android.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import se.yverling.lab.android.design.theme.LargeSpace

@Composable
fun SideNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    items: List<NavigationItem>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentParentRoute = navBackStackEntry?.destination?.parent?.route

    NavigationRail(modifier.padding(top = LargeSpace)) {
        items.forEach { item ->
            val selected = (currentRoute == item.route || currentParentRoute == item.route)

            NavigationRailItem(
                label = { Text(stringResource(item.label)) },
                icon = {
                    Icon(
                        if (selected) item.selectedIcon else item.icon,
                        contentDescription = stringResource(item.iconContentDescription)
                    )
                },
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                    }
                }
            )
        }
    }
}
