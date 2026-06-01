package se.yverling.lab.android.tv.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices.TV_1080p
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import se.yverling.lab.android.tv.app.R
import se.yverling.lab.android.tv.app.model.TitleItem
import se.yverling.lab.android.tv.app.model.titleItems
import se.yverling.lab.android.tv.app.ui.theme.AndroidLabThemeWrapper
import se.yverling.lab.android.tv.app.ui.theme.DefaultSpace
import se.yverling.lab.android.tv.app.ui.theme.LargeSpace
import se.yverling.lab.android.tv.app.ui.theme.VeryLargeSpace
import se.yverling.lab.android.tv.ui.backgroundBrush

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun StartScreen() {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(LargeSpace)
        ) {
            Text(
                text = stringResource(R.string.recommended_list_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
            )

            Spacer(modifier = Modifier.height(LargeSpace))

            LazyRow(
                modifier = Modifier.focusProperties {
                    onExit = {
                        when (requestedFocusDirection) {
                            // Prevents focus to jump to the main menu
                            FocusDirection.Right -> cancelFocusChange()
                        }
                    }
                },
                contentPadding = PaddingValues(
                    start = DefaultSpace,
                    end = DefaultSpace
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

                    Spacer(modifier = Modifier.width(LargeSpace))
                }
            }

            Spacer(modifier = Modifier.height(LargeSpace))

            Text(
                text = stringResource(R.string.my_favorites_list_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
            )

            Spacer(modifier = Modifier.height(LargeSpace))

            LazyRow(modifier = Modifier.focusProperties {
                onExit = {
                    when (requestedFocusDirection) {
                        // Prevents focus to jump to the main menu
                        FocusDirection.Next -> cancelFocusChange()
                    }
                }
            }, contentPadding = PaddingValues(horizontal = DefaultSpace)) {
                // Pick last three items just to simulate
                itemsIndexed(titleItems.takeLast(3)) { index, item ->
                    StreamingItemCard(item = item)
                    Spacer(modifier = Modifier.width(LargeSpace))
                }
            }
        }
    }
}

@Composable
private fun StreamingItemCard(
    modifier: Modifier = Modifier,
    item: TitleItem,
) {
    Card(
        onClick = {},
        colors = CardDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.34f),
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        modifier = modifier.width(200.dp),
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
                    modifier = Modifier.size(VeryLargeSpace)
                )
            }

            Column(modifier = Modifier.padding(DefaultSpace)) {
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

@Preview(device = TV_1080p)
@PreviewWrapper(AndroidLabThemeWrapper::class)
@Composable
private fun StartScreenPreview() {
    StartScreen()
}

@Preview
@PreviewWrapper(AndroidLabThemeWrapper::class)
@Composable
private fun StreamingItemCardPreview() {
    StreamingItemCard(item = titleItems.first(),)
}
