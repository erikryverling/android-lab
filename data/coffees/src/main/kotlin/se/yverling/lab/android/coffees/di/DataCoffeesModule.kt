package se.yverling.lab.android.coffees.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import se.yverling.lab.android.coffees.CoffeesRepository
import se.yverling.lab.android.coffees.CoffeesRepositoryImpl
import se.yverling.lab.android.coffees.db.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataCoffeesModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "coffees-database").build()
    }

    @Provides
    @Singleton
    internal fun provideCoffeeRepository(
        @ApplicationContext context: Context,
        db: AppDatabase,
    ): CoffeesRepository = CoffeesRepositoryImpl(
        context = context,
        db = db,
    )
}
