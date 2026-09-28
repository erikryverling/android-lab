package se.yverling.lab.android.data.weather.di

import android.content.Context
import androidx.datastore.core.DataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import se.yverling.lab.android.data.weather.GetAndCacheWeatherUseCase
import se.yverling.lab.android.data.weather.GetAndCacheWeatherUseCaseImpl
import se.yverling.lab.android.data.weather.WeatherDataStoreRepository
import se.yverling.lab.android.data.weather.WeatherDataStoreRepositoryImpl
import se.yverling.lab.android.data.weather.WeatherNetworkRepository
import se.yverling.lab.android.data.weather.WeatherNetworkRepositoryImpl
import se.yverling.lab.android.data.weather.currentWeatherDataStore
import se.yverling.lab.android.data.weather.network.WeatherApi
import javax.inject.Named
import javax.inject.Singleton

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

@Module
@InstallIn(SingletonComponent::class)
class DataWeatherModule {
    @Provides
    @Singleton
    @Named("kotlinSerializationJson")
    fun providesKotlinSerializationJson(): Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            defaultRequest {
                url(BASE_URL)
            }
        }
    }

    @Provides
    @Singleton
    fun provideCurrentWeatherDataStore(@ApplicationContext context: Context): DataStore<se.yverling.lab.android.CurrentWeather> =
        context.currentWeatherDataStore

    @Provides
    @Singleton
    internal fun provideWeatherDataStoreRepository(
        dataStore: DataStore<se.yverling.lab.android.CurrentWeather>
    ): WeatherDataStoreRepository = WeatherDataStoreRepositoryImpl(dataStore)

    @Provides
    @Singleton
    internal fun provideWeatherNetworkRepository(
        weatherApi: WeatherApi
    ): WeatherNetworkRepository = WeatherNetworkRepositoryImpl(weatherApi)

    @Provides
    @Singleton
    internal fun provideGetAndCacheWeatherUseCase(
        networkRepository: WeatherNetworkRepository,
        dataStoreRepository: WeatherDataStoreRepository
    ): GetAndCacheWeatherUseCase = GetAndCacheWeatherUseCaseImpl(networkRepository, dataStoreRepository)
}
