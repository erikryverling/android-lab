package se.yverling.lab.android.tv.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.tv.material3.MaterialTheme

val backgroundBrush: Brush
    @Composable
    get() = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        )
    )
