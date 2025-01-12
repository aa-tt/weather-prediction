package life.outorin.myday.service;

import life.outorin.myday.dto.Coord;
import life.outorin.myday.dto.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ConnectorService {

    @Autowired
    private WebClient webClient;

    @Value("${openweather.api.city}")
    private String cityUri;
    @Value("${openweather.api.weather}")
    private String weatherUri;
    @Value("${openweather.api.key}")
    private String weatherKey;

    @Cacheable(key= "#city", value= "City")
    public Mono<List<Coord>> getCity(String city) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(cityUri)
                        .queryParam("q", city)
                        .queryParam("appid", weatherKey)
                        .build())
                .retrieve()
                .bodyToFlux(Coord.class)
                .collectList();
    }

    @Cacheable(key="#coord.lat + '-' + #coord.lon", value= "WeatherData")
    public Mono<WeatherData> getWeather(Coord coord) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(weatherUri)
                        .queryParam("lat", coord.lat())
                        .queryParam("lon", coord.lon())
                        .queryParam("units", "imperial")
                        .queryParam("appid", weatherKey)
                        .build())
                .retrieve()
                .bodyToMono(WeatherData.class);
    }
}