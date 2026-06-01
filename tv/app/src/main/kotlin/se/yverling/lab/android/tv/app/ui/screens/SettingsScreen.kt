package se.yverling.lab.android.tv.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.TV_1080p
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import se.yverling.lab.android.tv.app.R
import se.yverling.lab.android.tv.app.ui.theme.AndroidLabThemeWrapper
import se.yverling.lab.android.tv.app.ui.theme.DefaultSpace
import se.yverling.lab.android.tv.app.ui.theme.LargeSpace
import se.yverling.lab.android.tv.ui.backgroundBrush

@Composable
internal fun SettingsScreen(modifier: Modifier = Modifier) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundBrush),
        contentAlignment = Alignment.Center
    ) {
        SettingsCard(Modifier.focusRequester(focusRequester))
    }
}

@Composable
private fun SettingsCard(modifier: Modifier = Modifier) {
    Card(
        onClick = {},
        colors = CardDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = modifier
            .padding(DefaultSpace)
    ) {
        Text(
            text = stringResource(R.string.settings_screen_card_title),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(LargeSpace)
        )
    }
}

@Preview(device = TV_1080p)
@PreviewWrapper(AndroidLabThemeWrapper::class)
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen()
}

@PreviewWrapper(AndroidLabThemeWrapper::class)
@Preview
@Composable
private fun SettingsCardPreview() {
    SettingsCard()
}
