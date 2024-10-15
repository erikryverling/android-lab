package se.yverling.lab.android.coffees.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Coffee::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coffeeDao(): CoffeesDao
}
