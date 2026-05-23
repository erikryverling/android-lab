package se.yverling.lab.android.ai.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import se.yverling.lab.android.ai.AiRepository
import se.yverling.lab.android.ai.AiRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataAiModule {
    @Provides
    @Singleton
    internal fun provideAiRepository(@ApplicationContext context: Context)
    : AiRepository = AiRepositoryImpl(context)
}
