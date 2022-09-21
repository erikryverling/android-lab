package se.yverling.lab.android.coffees

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import se.yverling.lab.android.coffees.db.AppDatabase
import se.yverling.lab.android.coffees.db.toCoffeeModel
import se.yverling.lab.android.coffees.io.CoffeesSampleData
import se.yverling.lab.android.coffees.model.Coffee
import javax.inject.Inject

class CoffeesRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val db: AppDatabase,
) {
    fun getList(): Flow<List<Coffee>> {
        return db.coffeeDao().getCoffees().map { coffees ->
            coffees.map { coffee ->
                coffee.toCoffeeModel()
            }
        }
    }

    suspend fun prePopulateList() {
        db.coffeeDao().setCoffees(CoffeesSampleData.get(context))
    }
}
