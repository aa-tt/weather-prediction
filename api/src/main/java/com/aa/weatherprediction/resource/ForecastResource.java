
package com.aa.weatherprediction.resource;

import com.aa.weatherprediction.model.DayAndReport;
import com.aa.weatherprediction.model.Report;
import com.aa.weatherprediction.service.ForecastService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController("/api/v1/forecast")
public class ForecastResource {

    private final ForecastService forecastService;

    public ForecastResource(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping(value = "/cities/{cityName}/forecast", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Report> getForecastByCity(@PathVariable String cityName) {
        return new ResponseEntity<>(forecastService.getForecastByCity(cityName), HttpStatus.OK);
    }

    @GetMapping("/cities/{cityName}/days/{days}/forecast")
    public ResponseEntity<ArrayList<DayAndReport>> getForecastByDays(
            @PathVariable String cityName,
            @PathVariable(required = false) Long days) {
        if (days == null) {
            days = 3L;
        }
        return new ResponseEntity<>(forecastService.getForecastByDays(cityName, days), HttpStatus.OK);
    }
}