package se.yverling.lab.android.design.theme

import androidx.compose.foundation.style.Style
import androidx.compose.foundation.style.StyleScope
import androidx.compose.foundation.style.*
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.ui.unit.dp

// Extensions for reading composition local tokens inside StyleScope
val StyleScope.colors: ColorScheme
    get() = LocalAndroidLabColors.currentValue

val StyleScope.typography: Typography
    get() = LocalAndroidLabTypography.currentValue

val StyleScope.shapes: Shapes
    get() = LocalAndroidLabShapes.currentValue

/**
 * Centrally defined Jetpack Compose Styles for the design system.
 */
object AndroidLabStyles {
    val miscButtonStyle: Style = Style {
        background(colors.primary)
        contentColor(colors.onPrimary)
        shape(shapes.medium)
        minWidth(120.dp)
        minHeight(48.dp)
        textStyle(typography.labelLarge)
        contentPaddingHorizontal(DefaultSpace)
        contentPaddingVertical(MediumSpace)

        // Pressed interaction state with micro-animations
        pressed {
            animate {
                scale(0.95f)
                background(colors.primaryContainer)
                contentColor(colors.onPrimaryContainer)
            }
        }

        // Disabled state styling
        disabled {
            background(colors.onSurface.copy(alpha = 0.12f))
            contentColor(colors.onSurface.copy(alpha = 0.38f))
        }
    }
}
