package se.yverling.lab.android.data.weather.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import se.yverling.lab.android.CurrentWeather
import java.io.InputStream
import java.io.OutputStream

internal const val DATASTORE_FILE_NAME = "current_weather.pb"

object CurrentWeatherSerializer : Serializer<CurrentWeather> {
    override val defaultValue: CurrentWeather = CurrentWeather.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): CurrentWeather {
        try {
            return CurrentWeather.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    override suspend fun writeTo(t: CurrentWeather, output: OutputStream) = t.writeTo(output)
}
