
package com.aa.weatherprediction.service;

import com.aa.weatherprediction.model.City;
import com.aa.weatherprediction.model.WeatherData;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.io.IOException;
import java.util.List;

@Service
@RefreshScope
@CacheConfig(cacheNames = {"cityCache", "weatherCache"})
public class ConnectorService {

    @Value("${weatherApi.key:default}")
    private String apiKey;
    private final String baseUrl = "https://api.openweathermap.org";

    private final Retrofit retrofit;
    private final ConnectorAPI api;

    public ConnectorService(OkHttpClientFactory okHttpClientFactory) {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClientFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        this.api = retrofit.create(ConnectorAPI.class);
    }

    interface ConnectorAPI {
        @GET("/geo/1.0/direct")
        Call<List<City>> getCity(
                @Query("q") String name,
                @Query("appId") String apiKey
        );

        @GET("/data/2.5/weather")
        Call<WeatherData> getWeather(
                @Query("lat") double lat,
                @Query("lon") double lon,
                @Query("units") String units,
                @Query("appId") String apiKey
        );
    }

    @Cacheable(key = "#name", value = "City")
    public List<City> getCity(String name) throws IOException {
        Response<List<City>> response = api.getCity(name, apiKey).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response.body();
    }

    @Cacheable(key = "#lat + '-' + #lon", value = "WeatherData")
    public WeatherData getWeather(double lat, double lon) throws IOException {
        Response<WeatherData> response = api.getWeather(lat, lon, "imperial", apiKey).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response.body();
    }
}

@Configuration
class OkHttpClientFactory {
    public OkHttpClient create() {
        return new OkHttpClient.Builder().build();
    }
}