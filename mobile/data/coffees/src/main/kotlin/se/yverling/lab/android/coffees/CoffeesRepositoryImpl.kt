package se.yverling.lab.android.coffees

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import se.yverling.lab.android.coffees.db.CoffeesDao
import se.yverling.lab.android.coffees.db.toCoffeeModel
import se.yverling.lab.android.coffees.io.CoffeesSampleData
import se.yverling.lab.android.common.model.Coffee

internal class CoffeesRepositoryImpl(
    private val dao: CoffeesDao,
    private val sampleDataProvider: suspend () -> List<se.yverling.lab.android.coffees.db.Coffee> = { emptyList() },
) : CoffeesRepository {
    constructor(
        context: Context,
        coffeesDao: CoffeesDao,
    ) : this(
        dao = coffeesDao,
        sampleDataProvider = { CoffeesSampleData.get(context) }
    )

    override fun getList(): Flow<List<Coffee>> {
        return dao.getCoffees().map { coffees ->
            coffees.map { coffee ->
                coffee.toCoffeeModel()
            }
        }
    }

    override suspend fun prePopulateList() {
        dao.setCoffees(sampleDataProvider())
    }
}
