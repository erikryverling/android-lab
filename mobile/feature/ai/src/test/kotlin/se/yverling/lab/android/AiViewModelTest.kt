package se.yverling.lab.android

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import se.yverling.lab.android.common.model.Coffee
import se.yverling.lab.android.test.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class AiViewModelTest {
    private val repository = FakeAiRepository()
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
        repository.emitCoffee(coffee)

        viewModel = AiViewModel(repository)

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
        repository.emitError(RuntimeException(errorMessage))

        viewModel = AiViewModel(repository)

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
