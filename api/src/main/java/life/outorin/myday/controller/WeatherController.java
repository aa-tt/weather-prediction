package life.outorin.myday.controller;

import life.outorin.myday.dto.Coord;
import life.outorin.myday.dto.Weather;
import life.outorin.myday.dto.WeatherData;
import life.outorin.myday.dto.WeatherReport;
import life.outorin.myday.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WeatherController {

    @Autowired
    private WebClient webClient;

    @Value("${openweather.api.city}")
    private String cityUri;
    @Value("${openweather.api.weather}")
    private String weatherUri;
    @Value("${openweather.api.key}")
    private String weatherKey;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/cities/{city}/forecast")
    public Mono<WeatherReport> getCurrentWeather(@PathVariable String city) {
        return weatherService.getReport(city);
    }

    @GetMapping("/cities/{city}/forecast/alerts")
    public SseEmitter getCurrentWeatherAlerts(@PathVariable String city) {
        return weatherService.getCurrentWeatherAlerts(city);
    }

    @GetMapping("/cities/{city}/days/{days}/forecast")
    public Mono<List<WeatherReport>> getPast3DaysWeather(@PathVariable String city, @PathVariable Integer days) {
        return weatherService.fetchWeatherReportHistoryFromCassandra(city);
    }
}
