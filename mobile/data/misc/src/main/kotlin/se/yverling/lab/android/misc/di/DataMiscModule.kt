package se.yverling.lab.android.misc.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import se.yverling.lab.android.misc.MiscRepository
import se.yverling.lab.android.misc.MiscRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataMiscModule {
    @Provides
    @Singleton
    internal fun provideMiscRepository(): MiscRepository = MiscRepositoryImpl()
}
