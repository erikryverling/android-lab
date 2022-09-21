package se.yverling.lab.android.feature.misc

import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import se.yverling.lab.android.misc.MiscRepository
import se.yverling.lab.android.test.MainDispatcherRule

@OptIn(ExperimentalCoroutinesApi::class)
class MiscViewModelTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    /**
     * We use UnconfinedTestDispatcher to avoid having to use advanceUntilIdle() See CoroutinesTest for more info.
     */
    @get:Rule
    val mainRule = MainDispatcherRule(UnconfinedTestDispatcher())

    @RelaxedMockK
    lateinit var repositoryMock: MiscRepository

    private lateinit var viewModel: MiscViewModel

    /**
     * We could've use Turbine's test() method in this case, which would make this easier, but
     * the purpose of this test is to get a deeper understanding on how to test StateFlow.
     */
    @Test
    fun `Should set UI state successfully`() = runTest {
        val fakeRepository = FakeRepository()

        every { repositoryMock.longRunningFlow() } returns fakeRepository.flow

        viewModel = MiscViewModel(repositoryMock)

        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect {
                println("Got UI state: $it")
            }
        }

        viewModel.uiState.value.shouldBeTypeOf<MiscViewModel.MiscUiState.State1>()
        fakeRepository.emit(0)
        viewModel.uiState.value.shouldBeTypeOf<MiscViewModel.MiscUiState.State1>()
        fakeRepository.emit(1)
        viewModel.uiState.value.shouldBeTypeOf<MiscViewModel.MiscUiState.State2>()
        fakeRepository.emit(2)
        viewModel.uiState.value.shouldBeTypeOf<MiscViewModel.MiscUiState.State1>()
        collectJob.cancel()
    }

    /**
     * We are using this to be able to control the emits.
     */
    class FakeRepository {
        val flow = MutableSharedFlow<Int>()

        suspend fun emit(number: Int) {
            println("Emitting: $number")
            flow.emit(number)
        }
    }
}
