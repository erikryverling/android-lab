package se.yverling.lab.android.coffees

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import se.yverling.lab.android.common.model.Coffee

class FakeCoffeesRepository(
    initialCoffees: List<Coffee> = emptyList(),
    private val sampleData: List<Coffee> = emptyList()
) : CoffeesRepository {
    private val coffeesFlow = MutableStateFlow(initialCoffees)
    var prePopulateCallCount = 0
        private set

    override fun getList(): Flow<List<Coffee>> = coffeesFlow

    override suspend fun prePopulateList() {
        prePopulateCallCount++
        coffeesFlow.value = sampleData
    }

    fun setCoffees(coffees: List<Coffee>) {
        coffeesFlow.value = coffees
    }
}
