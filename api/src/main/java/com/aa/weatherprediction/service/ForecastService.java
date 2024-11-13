
package com.aa.weatherprediction.service;

import com.aa.weatherprediction.model.DayAndReport;
import com.aa.weatherprediction.model.Report;
import com.aa.weatherprediction.model.WeatherData;
import com.aa.weatherprediction.model.City;
import com.aa.weatherprediction.model.Weather;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForecastService {

    private final ConnectorService connectorService;

    public ForecastService(ConnectorService connectorService) {
        this.connectorService = connectorService;
    }

    public Report getForecastByCity(String name) {
        City city = connectorService.getCity(name).get(0);
        WeatherData weatherData = connectorService.getWeather(city.getLat(), city.getLon());
        List<String> weatherAlerts = new ArrayList<>();
        if (weatherData.getWind().getSpeed() > 10) {
            weatherAlerts.add("It’s too windy, watch out!");
        }
        if (weatherData.getMain().getTemp() > 40) {
            weatherAlerts.add("Use sunscreen lotion");
        }
        if (weatherData.getRain() != null) {
            weatherAlerts.add("Carry Umbrella");
        }
        return new Report(
                weatherAlerts,
                weatherData.getWeather().stream().map(Weather::getMain).collect(Collectors.toList()),
                weatherData.getMain().getTempMin(),
                weatherData.getMain().getTempMax()
        );
    }

    public List<DayAndReport> getForecastByDays(String name, long days) {
        List<DayAndReport> reports = new ArrayList<>();
        for (long day = 1; day <= days; day++) {
            reports.add(new DayAndReport(
                    LocalDate.now().plusDays(day).getDayOfWeek().name(),
                    getForecastByCity(name)
            ));
        }
        return reports;
    }
}