package se.yverling.lab.android.feature.misc

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

const val MiscScreenDestination = "miscScreen"

@ExperimentalMaterial3Api
@SuppressLint("AutoboxingStateCreation", "UnrememberedMutableState")
@Composable
fun MiscScreen(
    onDeepLinkButtonClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    viewModel: MiscViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(pageCount = { Tabs.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    Column {
        PrimaryTabRow(selectedTabIndex = selectedTabIndex.value) {
            Tabs.entries.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        scope.launch {
                            pagerState.scrollToPage(index)
                        }
                    },
                    text = { Text(text = stringResource(tab.title)) },
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) {
            when (pagerState.currentPage) {
                0 -> {
                    MiscScreenContent(
                        scope = scope,
                        viewModel = viewModel,
                        onDeepLinkButtonClick = onDeepLinkButtonClick,
                    )
                }

                1 -> AnimationContent()

                else -> {
                    // Ignore
                }
            }
        }
    }
}

enum class Tabs(@StringRes val title: Int) {
    Misc(
        title = R.string.misc_tab_title,
    ),
    Animation(
        title = R.string.animation_tab_title
    )
}
