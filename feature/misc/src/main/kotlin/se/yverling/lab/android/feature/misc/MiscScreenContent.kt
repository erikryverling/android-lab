package se.yverling.lab.android.feature.misc

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import se.yverling.lab.android.design.theme.AndroidLabTheme
import se.yverling.lab.android.design.theme.DefaultSpace
import se.yverling.lab.android.design.theme.LargeSpace
import se.yverling.lab.android.design.theme.MediumSpace
import se.yverling.lab.android.design.theme.SmallSpace
import se.yverling.lab.android.feature.misc.theme.CarouselItemSize
import timber.log.Timber

private const val REMOTE_IMAGE_URL = "https://erik.r.yverling.com/twinkle-logo.png"

/*
    Annotating a class as @Immutable means that you vouch that is will never change,
    and Compose will then mark it as 'stable' instead of 'unstable'
 */
@Immutable
private data class Person(val names: MutableList<String>)

/*
    Annotating a class as @Stable means that you vouch that Compose will be notified,
    when it changes (usually through mutableState), even-though it's mutable.
    As with @Immutable this will make Compose mark it as 'stable' instead of 'unstable'.
 */
@Stable
private data class Employer(var name: String)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MiscScreenContent(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    onDeepLinkButtonClick: (() -> Unit)?,
    viewModel: MiscViewModel,
) {
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState()

    var showBottomSheet by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val dynamicTheme = remember { mutableStateOf(false) }

    var evenCounter = 0


    AndroidLabTheme(dynamicColor = dynamicTheme.value) {
        Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (showBottomSheet) {
                    ModalBottomSheet(
                        modifier = Modifier.fillMaxSize(),
                        sheetState = modalSheetState,
                        onDismissRequest = { showBottomSheet = false }
                    ) {
                        Row(
                            modifier = modifier
                                .padding(MediumSpace)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                modifier = Modifier.padding(end = MediumSpace),
                                onClick = {
                                    if (modalSheetState.currentValue == SheetValue.PartiallyExpanded) {
                                        coroutineScope.launch {
                                            modalSheetState.expand()
                                        }
                                    } else if (modalSheetState.currentValue == SheetValue.Expanded) {
                                        coroutineScope.launch {
                                            modalSheetState.partialExpand()
                                        }
                                    }
                                }
                            ) {
                                val text =
                                    if (modalSheetState.currentValue == SheetValue.PartiallyExpanded) {
                                        stringResource(
                                            R.string.bottom_sheet_button_toogle_fullscreen_button_title
                                        )
                                    } else {
                                        stringResource(
                                            R.string.bottom_sheet_button_toogle_half_expanded_button_title
                                        )
                                    }
                                Text(text)
                            }

                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        modalSheetState.hide()
                                        showBottomSheet = false
                                    }
                                }
                            ) {
                                Text(stringResource(R.string.bottom_sheet_button_hide_button_title))
                            }
                        }
                    }
                }

                Column(
                    modifier
                        .fillMaxSize()
                        .padding(DefaultSpace)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    /*
                        mutableState() will make sure that the composable is recomposed whenever the counter value is changed.
                        remember() will make sure that it's also saved during recomposition and not reset.
                        So in theory we could use only mutableState() if we move the counter outside of the composable
                     */
                    var manualRecomposeCount by remember { mutableIntStateOf(0) }

                    /*
                        This will crate a state value that only updates when the counter changes from being larger than 0
                        and will be more efficient than checking counter > 0 directly
                     */
                    val showSnackbar by remember {
                        derivedStateOf {
                            manualRecomposeCount > 0
                        }
                    }

                    /*
                        To call a suspended function from within a Composable we need to use a LaunchedEffect.
                        To control when the launched effect is called we provide a key. The launched effect will
                        then be run each time the containing composable is recomposed and the key has changed
                        value
                     */
                    if (showSnackbar) {
                        /*
                            This will guarantee to refer to the latest logNumberOfManualRecompositions() function that the closest
                            composable (Column) was recomposed with
                         */
                        val currentNumberOfManualRecompositions by rememberUpdatedState(
                            ::logNumberOfManualRecompositions
                        )

                        LaunchedEffect(manualRecomposeCount) {
                            scope.launch {
                                currentNumberOfManualRecompositions(manualRecomposeCount)
                                snackbarHostState.currentSnackbarData?.dismiss()

                                snackbarHostState.showSnackbar(
                                    message = "Number of manual recompositions: $manualRecomposeCount",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    }

                    MiscButton(
                        text = if (dynamicTheme.value) R.string.custom_theme else R.string.dynamic_theme,
                        modifier = Modifier.padding(bottom = LargeSpace, top = LargeSpace)
                    ) {
                        dynamicTheme.value = !dynamicTheme.value
                    }

                    onDeepLinkButtonClick?.let {
                        MiscButton(
                            text = R.string.deep_link_button_title,
                            modifier = Modifier.padding(bottom = LargeSpace)
                        ) { onDeepLinkButtonClick.invoke() }
                    }

                    MiscButton(
                        text = R.string.recompose_button_title,
                        modifier = Modifier.padding(bottom = LargeSpace)
                    ) {
                        manualRecomposeCount++
                        if (manualRecomposeCount % 2 == 0) evenCounter++
                    }

                    MiscButton(
                        text = R.string.bottom_sheet_button_title,
                        modifier = Modifier.padding(bottom = LargeSpace),
                    ) {
                        showBottomSheet = true
                    }

                    val state by viewModel.uiState.collectAsStateWithLifecycle()
                    Text(
                        modifier = Modifier.padding(bottom = LargeSpace),
                        text = "Collecting: ${state.name}",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )

                    SkippableComposable(
                        modifier = Modifier.padding(bottom = LargeSpace),
                        person = Person(mutableListOf("Immut", "Able")),
                        employer = Employer("Stable Inc.")
                    )

                    AsyncImage(
                        modifier = Modifier.padding(bottom = LargeSpace),
                        model = REMOTE_IMAGE_URL,
                        placeholder = painterResource(R.drawable.baseline_image_24),
                        error = painterResource(R.drawable.baseline_broken_image_24),
                        contentDescription = stringResource(
                            R.string.remote_image_content_description
                        ),
                        contentScale = ContentScale.Fit
                    )

                    AutoFillTextField(
                        modifier = Modifier.padding(bottom = SmallSpace),
                        InputType.EMAIL
                    )

                    AutoFillTextField(
                        modifier = Modifier.padding(bottom = LargeSpace),
                        InputType.PASSWORD
                    )

                    val segmentedButtonsCheckedList = remember { mutableStateListOf<Int>() }
                    val segmentedButtonsEntries = listOf(
                        R.string.segmented_button_1_title,
                        R.string.segmented_button_2_title,
                        R.string.segmented_button_3_title
                    )

                    MultiChoiceSegmentedButtonRow(
                        modifier = Modifier.padding(bottom = LargeSpace)
                    ) {
                        segmentedButtonsEntries.forEachIndexed { index, label ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(index = index, count = segmentedButtonsEntries.size),
                                onCheckedChange = {
                                    if (index in segmentedButtonsCheckedList) {
                                        segmentedButtonsCheckedList.remove(index)
                                    } else {
                                        segmentedButtonsCheckedList.add(index)
                                    }
                                },
                                checked = index in segmentedButtonsCheckedList
                            ) {
                                Text(stringResource(label))
                            }
                        }
                    }

                    val carouselItems = viewModel.carouselItems

                    HorizontalUncontainedCarousel(
                        modifier = Modifier.height(CarouselItemSize),
                        state = rememberCarouselState { carouselItems.count() },
                        itemWidth = CarouselItemSize,
                        itemSpacing = SmallSpace,
                        contentPadding = PaddingValues(horizontal = DefaultSpace)
                    ) { i ->
                        val item = carouselItems[i]
                        Image(
                            modifier = Modifier.maskClip(MaterialTheme.shapes.extraLarge),
                            painter = painterResource(id = item.imageResId),
                            contentDescription = stringResource(item.contentDescriptionResId),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // This would run on each recomposition.
                    // val list = createHugeList()

                    /* It's better to wrap it in a remember() and only update it when it makes sense.
                 In this case whenever evenCounter is changed.
                 Also note that the key to remember() could be of any type. */
                    val list by remember(evenCounter) { mutableStateOf(createHugeList()) }
                }
            }
        }
    }
}

private fun createHugeList(): List<Int> {
    Timber.d("Creating huge list!")
    val mutableList = mutableListOf<Int>()
    for (i in 0..1000) {
        mutableList.add(i)
    }
    return mutableList.toList()
}


@Composable
fun MiscButton(@StringRes text: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(modifier = modifier, onClick = onClick) {
        Text(
            stringResource(text),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun RecomposeButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(modifier = modifier, onClick = onClick) {
        Text(
            stringResource(R.string.recompose_button_title),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

// This Composable should be skipped during recomposition
@Composable
private fun SkippableComposable(modifier: Modifier = Modifier, person: Person, employer: Employer) {
    Text(
        modifier = modifier,
        text = "Name: ${person.names[0]} ${person.names[1]}, Employer: ${employer.name}",
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center
    )
}

/**
 * This does currently not work with 1Password, since it's not setting the correct
 * inputType. AutofillType.Password / KeyboardType.Password works as expected though.
 * Try this out every now and then do see if Google has added support for it in Compose.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AutoFillTextField(modifier: Modifier = Modifier, inputType: InputType) {
    var value by remember { mutableStateOf("") }

    val autofillNode = AutofillNode(
        autofillTypes =
        if (inputType == InputType.EMAIL) {
            listOf(AutofillType.EmailAddress)
        } else {
            listOf(AutofillType.Password)
        },
        onFill = { value = it }
    )
    val autofill = LocalAutofill.current

    LocalAutofillTree.current += autofillNode

    OutlinedTextField(
        modifier = modifier
            .onGloballyPositioned {
                autofillNode.boundingBox = it.boundsInWindow()
            }
            .onFocusChanged { focusState ->
                autofill?.run {
                    if (focusState.isFocused) {
                        requestAutofillForNode(autofillNode)
                    } else {
                        cancelAutofillForNode(autofillNode)
                    }
                }
            },
        value = value,
        label = { Text(text = if (inputType == InputType.EMAIL) "Email" else "Password") },
        keyboardOptions =
        if (inputType == InputType.EMAIL) {
            KeyboardOptions(keyboardType = KeyboardType.Email)
        } else {
            KeyboardOptions(keyboardType = KeyboardType.Password)
        },
        visualTransformation =
        if (inputType == InputType.EMAIL) VisualTransformation.None else PasswordVisualTransformation(),
        onValueChange = { value = it },
    )
}

enum class InputType {
    EMAIL,
    PASSWORD
}

fun logNumberOfManualRecompositions(counter: Int) {
    Timber.d("Number of manual recompositions: $counter")
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun MiscButtonPreview() {
    AndroidLabTheme {
        MiscButton(text = R.string.deep_link_button_title) {}
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun RecomposeButtonPreview() {
    AndroidLabTheme {
        RecomposeButton {}
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun SkippableComposablePreview() {
    AndroidLabTheme {
        SkippableComposable(
            person = Person(mutableListOf("Cloud", "Strife")),
            employer = Employer("Shinra")
        )
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun AutoFillTextFieldPreview() {
    AndroidLabTheme {
        Surface {
            AutoFillTextField(inputType = InputType.EMAIL)
        }
    }
}
