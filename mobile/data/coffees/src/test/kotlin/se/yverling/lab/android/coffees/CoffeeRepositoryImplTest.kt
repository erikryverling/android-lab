package se.yverling.lab.android.coffees

import android.content.Context
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import se.yverling.lab.android.coffees.db.AppDatabase
import se.yverling.lab.android.coffees.db.Coffee

@ExtendWith(MockKExtension::class)
class CoffeeRepositoryImplTest {
    @RelaxedMockK
    lateinit var appDatabaseMock: AppDatabase

    @MockK
    lateinit var contextMock: Context

    private lateinit var coffeesRepository: CoffeesRepository

    @BeforeEach
    fun setUp() {
        coffeesRepository = CoffeesRepositoryImpl(
            context = contextMock,
            db = appDatabaseMock,
        )
    }

    @Test
    fun `getCoffees() should get list successfully`() {
        val dbCoffee = Coffee(
            uid = 0,
            name = "Odo Carbonic",
            roaster = "Gringo Nordic",
            origin = "Ethiopia",
            region = "Guji",
        )

        val coffee = se.yverling.lab.android.common.model.Coffee(
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
