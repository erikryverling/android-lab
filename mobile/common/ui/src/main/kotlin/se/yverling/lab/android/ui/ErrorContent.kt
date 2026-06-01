package se.yverling.lab.android.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import se.yverling.lab.android.design.theme.AndroidLabThemeWrapper
import se.yverling.lab.android.design.theme.DefaultSpace

@Composable
fun ErrorContent(errorMessage: String) {
    Text(
        errorMessage,
        Modifier.padding(bottom = DefaultSpace),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.error,
        textAlign = TextAlign.Center
    )
}

@Composable
fun RetryButton(onRetryButtonClicked: () -> Unit) {
    Button(onClick = onRetryButtonClicked) {
        Text(stringResource(R.string.retry_button_title))
    }
}


@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@PreviewWrapper(AndroidLabThemeWrapper::class)
@Composable
private fun ErrorContentPreview() {
    ErrorContent("Something went wrong")
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@PreviewWrapper(AndroidLabThemeWrapper::class)
@Composable
private fun RetryButtonPreview() {
    RetryButton {}
}
