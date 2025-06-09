package se.yverling.lab.android.data.weather.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
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
}
