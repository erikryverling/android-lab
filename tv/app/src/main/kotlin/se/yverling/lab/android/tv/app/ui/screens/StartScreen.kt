package se.yverling.lab.android.tv.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.tooling.preview.Devices.TV_1080p
import androidx.compose.ui.tooling.preview.Preview
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
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "Recommended",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.height(32.dp))

            LazyRow(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp
                ),
            ) {
                itemsIndexed(titleItems) { index, item ->
                    StreamingItemCard(
                        item = item,
                        modifier = Modifier
                            .then(
                                // Give focus to first item
                                if (index == 0) {
                                    Modifier.focusRequester(focusRequester)
                                } else {
                                    Modifier
                                }
                            )
                    )

                    Spacer(modifier = Modifier.width(32.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "My favorites",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.height(32.dp))

            LazyRow(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp
                ),
            ) {
                itemsIndexed(titleItems.takeLast(3)) { index, item ->
                    StreamingItemCard(
                        item = item,
                        modifier = Modifier
                            .then(
                                // Give focus to first item
                                if (index == 0) {
                                    Modifier.focusRequester(focusRequester)
                                } else {
                                    Modifier
                                }
                            )
                    )

                    Spacer(modifier = Modifier.width(32.dp))
                }
            }
        }
    }
}

@Composable
private fun StreamingItemCard(
    item: TitleItem,
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
                    tint = Color.White,
                    modifier = Modifier.size(64.dp)
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
            }
        }
    }
}

private data class TitleItem(
    val title: String,
    val icon: ImageVector,
    val accentColor: Color,
)

private val titleItems = listOf(
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

@Preview
@Composable
private fun StreamingItemCardPreview() {
    // TODO Add AndroidLabTheme as preview
    MaterialTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
        ) {
            StreamingItemCard(item = titleItems.first())
        }
    }
}

@Preview(device = TV_1080p)
@Composable
private fun StartScreenPreview() {
    MaterialTheme {
        StartScreen()
    }
}
