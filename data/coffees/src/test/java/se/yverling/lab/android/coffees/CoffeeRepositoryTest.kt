package se.yverling.lab.android.coffees

import android.content.Context
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import se.yverling.lab.android.coffees.db.AppDatabase
import se.yverling.lab.android.coffees.db.Coffee

class CoffeeRepositoryTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    lateinit var appDatabaseMock: AppDatabase

    @MockK
    lateinit var contextMock: Context

    private lateinit var coffeesRepository: CoffeesRepository

    @Before
    fun setUp() {
        coffeesRepository = CoffeesRepository(
            context = contextMock,
            db = appDatabaseMock,
        )
    }

    @Test
    fun `Should get list successfully`() {
        val dbCoffee = Coffee(
            uid = 0,
            name = "Odo Carbonic",
            roaster = "Gringo Nordic",
            origin = "Ethiopia",
            region = "Guji",
        )

        val coffee = se.yverling.lab.android.coffees.model.Coffee(
            id = 0,
            name = "Odo Carbonic",
            roaster = "Gringo Nordic",
            origin = "Ethiopia",
            region = "Guji",
        )

        every { appDatabaseMock.coffeeDao().getCoffees() } returns flowOf(listOf(dbCoffee))

        runTest {
            coffeesRepository.getList().collect {
                it.shouldBe(listOf(coffee))
            }
        }
    }
}
