package se.yverling.lab.android.feature.misc

import io.kotest.matchers.types.shouldBeTypeOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import se.yverling.lab.android.test.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
@OptIn(ExperimentalCoroutinesApi::class)
class MiscViewModelTest {
    /**
     * We use UnconfinedTestDispatcher to avoid having to use advanceUntilIdle() See CoroutinesTest for more info.
     */
    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension(UnconfinedTestDispatcher())

    private lateinit var viewModel: MiscViewModel

    /**
     * We could've use Turbine's test() method in this case, which would make this easier, but
     * the purpose of this test is to get a deeper understanding on how to test StateFlow.
     */
    @Test
    fun `uiState should be set successfully`() = runTest {
        val miscRepository = FakeMiscRepository()

        viewModel = MiscViewModel(miscRepository)

        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect {
                println("Got UI state: $it")
            }
        }

        viewModel.uiState.value.shouldBeTypeOf<MiscViewModel.MiscUiState.State1>()
        miscRepository.emit(0)
        viewModel.uiState.value.shouldBeTypeOf<MiscViewModel.MiscUiState.State1>()
        miscRepository.emit(1)
        viewModel.uiState.value.shouldBeTypeOf<MiscViewModel.MiscUiState.State2>()
        miscRepository.emit(2)
        viewModel.uiState.value.shouldBeTypeOf<MiscViewModel.MiscUiState.State1>()
        collectJob.cancel()
    }
}
