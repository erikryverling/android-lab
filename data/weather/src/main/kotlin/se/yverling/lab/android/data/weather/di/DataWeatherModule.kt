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
import okhttp3.MediaType
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import se.yverling.lab.android.data.weather.network.WeatherService
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

    @Provides
    @Singleton
    @Named("kotlinSerialization")
    fun providesKotlinSerializationJsonConverterFactory(
        @Named("kotlinSerializationJson") json: Json,
    ): Converter.Factory {
        val contentType = MediaType.get("application/json")
        return json.asConverterFactory(contentType)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        @Named("kotlinSerialization") kotlinSerializationJsonConverterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(kotlinSerializationJsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherService(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
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
