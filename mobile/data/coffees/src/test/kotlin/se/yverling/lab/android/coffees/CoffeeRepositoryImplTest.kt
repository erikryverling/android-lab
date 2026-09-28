package se.yverling.lab.android.coffees

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import se.yverling.lab.android.coffees.db.Coffee

class CoffeeRepositoryImplTest {
    @Test
    fun `getList() should get list successfully`() {
        val dbCoffee = Coffee(
            uid = 0,
            name = "Odo Carbonic",
            roaster = "Gringo Nordic",
            origin = "Ethiopia",
            region = "Guji",
        )

        val expectedCoffee = se.yverling.lab.android.common.model.Coffee(
            id = 0,
            name = "Odo Carbonic",
            roaster = "Gringo Nordic",
            origin = "Ethiopia",
            region = "Guji",
        )

        val repository = CoffeesRepositoryImpl(
            dao = FakeCoffeesDao(listOf(dbCoffee)),
            sampleDataProvider = { emptyList() }
        )

        runTest {
            val result = repository.getList().first()
            result.shouldBe(listOf(expectedCoffee))
        }
    }

    @Test
    fun `prePopulateList() should insert sample data successfully`() {
        val sampleDbCoffee = Coffee(
            uid = 1,
            name = "Sample Coffee",
            roaster = "Sample Roaster",
            origin = "Sample Origin",
            region = "Sample Region",
        )

        val fakeDao = FakeCoffeesDao()
        val repository = CoffeesRepositoryImpl(
            dao = fakeDao,
            sampleDataProvider = { listOf(sampleDbCoffee) }
        )

        runTest {
            repository.prePopulateList()
            fakeDao.insertedCoffees.shouldBe(listOf(sampleDbCoffee))
        }
    }
}
