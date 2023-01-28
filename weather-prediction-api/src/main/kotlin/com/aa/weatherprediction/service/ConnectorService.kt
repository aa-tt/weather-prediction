package com.aa.weatherprediction.service

import com.aa.weatherprediction.model.City
import com.aa.weatherprediction.model.WeatherData
import okhttp3.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException


@Service
@CacheConfig(cacheNames = ["cityCache", "weatherCache"])
class ConnectorService {

    @Value("\${weatherApi.key:default}")
    private val apiKey: String = ""
    private val baseUrl: String = "https://api.openweathermap.org"

    private var retrofit: Retrofit
    private var api: ConnectorAPI

    constructor(
        okHttpClientFactory: OkHttpClientFactory,
    ) {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClientFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        api = retrofit.create(ConnectorAPI::class.java)
    }

    interface ConnectorAPI {
        @GET("/geo/1.0/direct")
        fun getCity(
            @Query("q") name: String,
            @Query("appId") apiKey: String,
        ): Call<List<City>>

        @GET("/data/2.5/weather")
        fun getWeather(
            @Query("lat") lat: Double,
            @Query("lon") lon: Double,
            @Query("units") units: String,
            @Query("appId") apiKey: String,
        ): Call<WeatherData>
    }

    @Cacheable(key= "#name", value= ["City"])
    fun getCity(name: String): List<City> {
        val response: Response<List<City>> = api.getCity(name, apiKey).execute()
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        return response.body()!!
    }

    @Cacheable(key="#lat-#lon", value= ["WeatherData"])
    fun getWeather(lat: Double, lon: Double): WeatherData {
        val response: Response<WeatherData> = api.getWeather(lat, lon, "imperial", apiKey).execute()
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        return response.body()!!
    }
}

@Configuration
class OkHttpClientFactory {
    fun create(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }
}
