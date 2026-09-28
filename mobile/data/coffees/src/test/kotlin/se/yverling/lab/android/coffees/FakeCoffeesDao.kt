package se.yverling.lab.android.coffees

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import se.yverling.lab.android.coffees.db.Coffee
import se.yverling.lab.android.coffees.db.CoffeesDao

class FakeCoffeesDao(initialCoffees: List<Coffee> = emptyList()) : CoffeesDao {
    private val coffeesFlow = MutableStateFlow(initialCoffees)
    var insertedCoffees: List<Coffee> = emptyList()
        private set

    override fun getCoffees(): Flow<List<Coffee>> = coffeesFlow

    override suspend fun setCoffees(coffeeEntities: List<Coffee>) {
        insertedCoffees = coffeeEntities
        coffeesFlow.value = coffeeEntities
    }
}
