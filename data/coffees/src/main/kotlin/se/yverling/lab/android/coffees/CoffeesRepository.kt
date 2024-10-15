package se.yverling.lab.android.coffees;

import kotlinx.coroutines.flow.Flow
import se.yverling.lab.android.common.model.Coffee

interface CoffeesRepository {
    fun getList(): Flow<List<Coffee>>
    suspend fun prePopulateList()
}
