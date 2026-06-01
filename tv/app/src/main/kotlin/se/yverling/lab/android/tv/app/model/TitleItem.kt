package se.yverling.lab.android.tv.app.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

internal data class TitleItem(
    val title: String,
    val icon: ImageVector,
    val accentColor: Color,
)

internal val titleItems = listOf(
    TitleItem(
        title = "Moon Harbor",
        icon = Icons.Default.Search,
        accentColor = Color(0xFF0EA5A3),
    ),
    TitleItem(
        title = "The Last Lighthouse",
        icon = Icons.Default.Star,
        accentColor = Color(0xFFD97706),
    ),
    TitleItem(
        title = "Kitchen Knights",
        icon = Icons.Default.ThumbUp,
        accentColor = Color(0xFF7C3AED),
    ),
    TitleItem(
        title = "Signal Valley",
        icon = Icons.Default.Info,
        accentColor = Color(0xFF2563EB),
    ),
    TitleItem(
        title = "Sunday Orbit",
        icon = Icons.Default.Favorite,
        accentColor = Color(0xFFE11D48),
    ),
    TitleItem(
        title = "Agent Pancake",
        icon = Icons.Default.Person,
        accentColor = Color(0xFF16A34A),
    ),
)
