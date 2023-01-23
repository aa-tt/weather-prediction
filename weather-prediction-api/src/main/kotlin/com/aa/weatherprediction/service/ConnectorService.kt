package com.aa.weatherprediction.service

import com.aa.weatherprediction.model.City
import com.aa.weatherprediction.model.WeatherData
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.io.IOException
import java.util.*


@Service
@CacheConfig(cacheNames = ["cityCache", "weatherCache"])
class ConnectorService {

    val devKey = "91c42c81f454e0988288906a426e66a1";
    val hostUrlForGeo = "https://api.openweathermap.org/geo/1.0/direct"
    val hostUrlForWeather = "https://api.openweathermap.org/data/2.5/weather"
    val client: OkHttpClient = OkHttpClient()
    val moshi: Moshi = Moshi.Builder().build()

    @Cacheable(key= "#name", value= ["City"])
    fun getCity(name: String): Optional<City> {
        val request: Request = Request.Builder()
            .url("$hostUrlForGeo?q=$name&appid=$devKey")
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            println("city service")

            val listOfCityType = Types.newParameterizedType(List::class.java, City::class.java)
            val cityAdapter = moshi.adapter<List<City>>(listOfCityType)
            val cityList = cityAdapter.fromJson(response.body!!.source())
            return Optional.ofNullable(cityList?.firstOrNull())
        }
    }

    @Cacheable(key="#lat-#lon", value= ["WeatherData"])
    fun getWeather(lat: Double, lon: Double): WeatherData {
        val request: Request = Request.Builder()
            .url("$hostUrlForWeather?lat=$lat&lon=$lon&units=imperial&appid=$devKey")
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val weatherAdapter = moshi.adapter(WeatherData::class.java)
            val weatherData = weatherAdapter.fromJson(response.body!!.source())
            return weatherData!!
        }
    }
}
