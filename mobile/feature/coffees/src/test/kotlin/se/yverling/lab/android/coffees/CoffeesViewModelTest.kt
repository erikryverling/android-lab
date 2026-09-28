package se.yverling.lab.android.coffees

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import se.yverling.lab.android.common.model.Coffee
import se.yverling.lab.android.test.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class CoffeesViewModelTest {
    private val coffees = listOf(
        Coffee(
            id = 0,
            name = "Odo Carbonic",
            roaster = "Gringo Nordic",
            origin = "Ethiopia",
            region = "Guji",
        )
    )

    @Test
    fun `uiState should emit coffees successfully`() {
        val repository = FakeCoffeesRepository(initialCoffees = coffees)
        val viewModel = CoffeesViewModel(repository)

        runTest {
            viewModel.uiState.test {
                val successItem = awaitItem()
                successItem.shouldBeTypeOf<CoffeesUiState.Success>()
                successItem.coffees.shouldBe(coffees)

                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `coffeesViewModel should prepopulate coffees successfully`() {
        val fakeRepository = FakeCoffeesRepository(
            initialCoffees = emptyList(),
            sampleData = coffees
        )
        val viewModel = CoffeesViewModel(fakeRepository)

        runTest {
            viewModel.uiState.test {
                val successItem = awaitItem()
                successItem.shouldBeTypeOf<CoffeesUiState.Success>()
                successItem.coffees.shouldBe(coffees)

                cancelAndConsumeRemainingEvents()
            }

            fakeRepository.prePopulateCallCount.shouldBe(1)
        }
    }
}
