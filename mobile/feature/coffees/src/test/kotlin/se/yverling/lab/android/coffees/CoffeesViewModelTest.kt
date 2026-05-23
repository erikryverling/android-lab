package se.yverling.lab.android.coffees

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import se.yverling.lab.android.common.model.Coffee
import se.yverling.lab.android.test.MainDispatcherExtension

@ExtendWith(MockKExtension::class)
@ExtendWith(MainDispatcherExtension::class)
class CoffeesViewModelTest {

    @RelaxedMockK
    lateinit var repositoryMock: CoffeesRepository

    @BeforeEach
    fun setup() {
        viewModel = CoffeesViewModel(repositoryMock)
    }

    private lateinit var viewModel: CoffeesViewModel

    @Test
    fun `uiState should emit coffees successfully`() {
        every { repositoryMock.getList() } returns flowOf(coffees)

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
        var isContainingSampleData = false

        coEvery {
            repositoryMock.prePopulateList()
        } answers { isContainingSampleData = true }

        every { repositoryMock.getList() } returns flow {
            if (!isContainingSampleData) {
                emit(listOf())
            } else {
                emit(coffees)
            }
        }

        runTest {
            viewModel.uiState.test {
                val successItem = awaitItem()
                successItem.shouldBeTypeOf<CoffeesUiState.Success>()
                successItem.coffees.shouldBe(coffees)

                cancelAndConsumeRemainingEvents()
            }

            coVerify { repositoryMock.prePopulateList() }
        }
    }

    private val coffees = listOf(
        Coffee(
            id = 0,
            name = "Odo Carbonic",
            roaster = "Gringo Nordic",
            origin = "Ethiopia",
            region = "Guji",
        )
    )
}
