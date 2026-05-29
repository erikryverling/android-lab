package se.yverling.lab.android.tv.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text

@Composable
fun StartScreen(
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    )
                )
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 96.dp, end = 48.dp)
        ) {
            Text(
                text = "Tonight's Picks",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = "Fresh stories, odd journeys, and comfort rewatches",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.78f),
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            LazyRow(
                contentPadding = PaddingValues(end = 48.dp),
            ) {
                itemsIndexed(streamingItems) { index, item ->
                    StreamingItemCard(
                        item = item,
                        modifier = Modifier
                            .then(
                                if (index == 0) {
                                    Modifier.focusRequester(focusRequester)
                                } else {
                                    Modifier
                                }
                            )
                    )

                    Spacer(modifier = Modifier.width(24.dp))
                }
            }
        }
    }
}

@Composable
private fun StreamingItemCard(
    item: StreamingItem,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = {},
        colors = CardDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.34f),
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        modifier = modifier.width(208.dp),
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 10f)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                item.accentColor.copy(alpha = 0.92f),
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier.size(56.dp)
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = item.detail,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.76f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = item.tagline,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.82f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }
            }
        }
    }
}

private data class StreamingItem(
    val title: String,
    val detail: String,
    val tagline: String,
    val icon: ImageVector,
    val accentColor: Color,
)

private val streamingItems = listOf(
    StreamingItem(
        title = "Moon Harbor",
        detail = "Sci-fi mystery - 8 episodes",
        tagline = "Continue",
        icon = Icons.Default.Search,
        accentColor = Color(0xFF0EA5A3),
    ),
    StreamingItem(
        title = "The Last Lighthouse",
        detail = "Nordic drama - New season",
        tagline = "New",
        icon = Icons.Default.Star,
        accentColor = Color(0xFFD97706),
    ),
    StreamingItem(
        title = "Kitchen Knights",
        detail = "Feel-good competition",
        tagline = "Top 10",
        icon = Icons.Default.ThumbUp,
        accentColor = Color(0xFF7C3AED),
    ),
    StreamingItem(
        title = "Signal Valley",
        detail = "Tech thriller - 2026",
        tagline = "Trending",
        icon = Icons.Default.Info,
        accentColor = Color(0xFF2563EB),
    ),
    StreamingItem(
        title = "Sunday Orbit",
        detail = "Cozy anthology",
        tagline = "Relax",
        icon = Icons.Default.Favorite,
        accentColor = Color(0xFFE11D48),
    ),
    StreamingItem(
        title = "Agent Pancake",
        detail = "Family adventure",
        tagline = "Premiere",
        icon = Icons.Default.Person,
        accentColor = Color(0xFF16A34A),
    ),
)
