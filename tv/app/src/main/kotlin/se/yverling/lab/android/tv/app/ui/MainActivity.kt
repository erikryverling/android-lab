package se.yverling.lab.android.tv.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.DrawerValue
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.NavigationDrawer
import androidx.tv.material3.NavigationDrawerItem
import androidx.tv.material3.NavigationDrawerItemDefaults
import androidx.tv.material3.NavigationDrawerScope
import androidx.tv.material3.Text
import androidx.tv.material3.rememberDrawerState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable
import se.yverling.lab.android.tv.app.R
import se.yverling.lab.android.tv.app.ui.screens.SettingsScreen
import se.yverling.lab.android.tv.app.ui.screens.StartScreen
import se.yverling.lab.android.tv.app.ui.theme.AndroidLabTheme
import se.yverling.lab.android.tv.app.ui.theme.DefaultSpace
import se.yverling.lab.android.tv.app.ui.theme.MediumSpace

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidLabTheme {
                val navController = rememberNavController()

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.mainMenuItem ?: MainMenuItem.Start

    NavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                currentRoute = currentRoute,
                isDrawerOpen = drawerState.currentValue == DrawerValue.Open,
                navController = navController
            )
        }
    ) {
        NavHost(
            navController = navController as NavHostController,
            startDestination = MainMenuItem.Start,
        ) {
            composable<MainMenuItem.Start> { StartScreen() }
            composable<MainMenuItem.Settings> { SettingsScreen() }
        }
    }
}

@Serializable
private sealed interface MainMenuItem {
    @Serializable
    data object Start : MainMenuItem

    @Serializable
    data object Settings : MainMenuItem
}

private val NavDestination.mainMenuItem: MainMenuItem?
    get() = when {
        hasRoute<MainMenuItem.Start>() -> MainMenuItem.Start
        hasRoute<MainMenuItem.Settings>() -> MainMenuItem.Settings
        else -> null
    }

@Composable
private fun NavigationDrawerScope.DrawerContent(
    currentRoute: MainMenuItem,
    isDrawerOpen: Boolean,
    navController: NavController
) {
    val startFocusRequester = remember { FocusRequester() }
    val settingsFocusRequester = remember { FocusRequester() }

    val drawerItemColors = NavigationDrawerItemDefaults.colors(
        selectedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        selectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,

        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
        focusedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,

        focusedSelectedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        focusedSelectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    LaunchedEffect(isDrawerOpen, currentRoute) {
        if (isDrawerOpen) {
            when (currentRoute) {
                MainMenuItem.Start -> startFocusRequester.requestFocus()
                MainMenuItem.Settings -> settingsFocusRequester.requestFocus()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(DefaultSpace),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(DefaultSpace))

        // Start
        NavigationDrawerItem(
            selected = currentRoute == MainMenuItem.Start,
            modifier = Modifier.focusRequester(startFocusRequester),
            onClick = {
                if (currentRoute != MainMenuItem.Start) {
                    navController.navigate(MainMenuItem.Start) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            colors = drawerItemColors,
        ) {
            Text(
                text = stringResource(R.string.start_menu_item_title),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = MediumSpace)
            )
        }

        Spacer(modifier = Modifier.height(DefaultSpace))

        // Settings
        NavigationDrawerItem(
            selected = currentRoute == MainMenuItem.Settings,
            modifier = Modifier.focusRequester(settingsFocusRequester),
            onClick = {
                if (currentRoute != MainMenuItem.Settings) {
                    navController.navigate(MainMenuItem.Settings) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
            colors = drawerItemColors,
        ) {
            Text(
                text = stringResource(R.string.settings_menu_item_title),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = MediumSpace)
            )
        }
    }
}
