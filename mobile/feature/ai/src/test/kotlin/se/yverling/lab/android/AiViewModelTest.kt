package se.yverling.lab.android

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import se.yverling.lab.android.ai.AiRepository
import se.yverling.lab.android.common.model.Coffee
import se.yverling.lab.android.test.MainDispatcherExtension

@ExtendWith(MockKExtension::class)
@ExtendWith(MainDispatcherExtension::class)
class AiViewModelTest {
    @RelaxedMockK
    lateinit var repositoryMock: AiRepository

    private lateinit var viewModel: AiViewModel

    @Test
    fun `uistate should emit coffee on success`() {
        val coffee = Coffee(
            id = 1,
            name = "Espresso",
            roaster = "Roaster",
            origin = "Ethiopia",
            region = "Yirgacheffe"
        )
        every { repositoryMock.promptFlow() } returns flowOf(coffee)

        viewModel = AiViewModel(repositoryMock)

        runTest {
            viewModel.uiState.test {
                val successItem = awaitItem()
                successItem.shouldBeTypeOf<AiViewModel.AiUiState.Success>()
                successItem.coffee.shouldBe(coffee)

                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `uistate should emit error on exception`() {
        val errorMessage = "Network timeout"
        every { repositoryMock.promptFlow() } returns flow { throw RuntimeException(errorMessage) }

        viewModel = AiViewModel(repositoryMock)

        runTest {
            viewModel.uiState.test {
                val errorItem = awaitItem()
                errorItem.shouldBeTypeOf<AiViewModel.AiUiState.Error>()
                errorItem.message.shouldBe(errorMessage)

                cancelAndConsumeRemainingEvents()
            }
        }
    }
}
