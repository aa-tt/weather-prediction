package life.outorin.myday.service;

import life.outorin.myday.dto.Weather;
import life.outorin.myday.dto.WeatherData;
import life.outorin.myday.dto.WeatherReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

    public WeatherReport generateReport(WeatherData weatherData) {
        logger.info("WeatherData: {}", weatherData);

        List<String> weatherAlerts = new ArrayList<>();
        if (weatherData.wind().speed() > 10) {
            weatherAlerts.add("It’s too windy, watch out!");
        }
        if (weatherData.main().temp() > 40) {
            weatherAlerts.add("Use sunscreen lotion");
        }
        if (weatherData.rain() != null) {
            weatherAlerts.add("Carry Umbrella");
        }

        return new WeatherReport(
                weatherAlerts,
                weatherData.weather().stream().map(Weather::main).collect(Collectors.toList()),
                weatherData.main().temp_min(),
                weatherData.main().temp_max(),
                WeatherReport.convertToDateTime(weatherData.dt())
        );
    }
}