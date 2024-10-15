package se.yverling.lab.android.coffees.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CoffeesDao {
    @Query("SELECT * FROM coffee")
    fun getCoffees(): Flow<List<Coffee>>

    @Insert
    suspend fun setCoffees(coffeeEntities: List<Coffee>)
}
