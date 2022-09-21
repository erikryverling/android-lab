package se.yverling.lab.android.data.weather.model

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import javax.inject.Inject

/*
    Preferably we should not use a UseCase here, but merge WeatherNetworkRepository and
    WeatherNetworkRepository into a WeatherRepository and to the below logic there.

    UseCases are more for combining different kinds of data (Repositories). Maybe we can
    add an example of that as well moving forward?
 */
class GetAndCacheWeatherUseCase @Inject constructor(
    private val networkRepository: WeatherNetworkRepository,
    private val dataStoreRepository: WeatherDataStoreRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<CurrentWeather> {
        return dataStoreRepository.fetchCurrentWeather()
            .flatMapMerge { currentWeatherCreatedAt ->
                if (today(currentWeatherCreatedAt.second)) {
                    flowOf(currentWeatherCreatedAt.first)
                } else {
                    networkRepository.getCurrentWeather()
                        .onEach { currentWeather ->
                            dataStoreRepository.persistCurrentWeather(
                                currentWeather,
                                Clock.System.now().toEpochMilliseconds()
                            )
                        }
                }
            }
    }

    private fun today(timeStamp: Long): Boolean {
        return timeStamp != -1L && (
                Instant.fromEpochMilliseconds(timeStamp)
                    .daysUntil(Clock.System.now(), TimeZone.currentSystemDefault()) == 0
                )
    }
}
