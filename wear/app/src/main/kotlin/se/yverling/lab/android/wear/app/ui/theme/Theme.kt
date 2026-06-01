package se.yverling.lab.android.wear.app.ui.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material3.ColorScheme
import androidx.wear.compose.material3.MaterialTheme

private val colorScheme = ColorScheme(
    primary = md_theme_dark_primary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimary = md_theme_dark_onPrimary,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondary = md_theme_dark_onSecondary,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    onSurface = md_theme_dark_onSurface,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
)

@Composable
fun WearAppTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
