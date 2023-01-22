package com.aa.weatherprediction.resource

import com.aa.weatherprediction.model.WeatherData
import com.aa.weatherprediction.service.ForecastService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController("/api/v1/forecast")
class ForecastResource(
    private val forecastService: ForecastService
) {

    @GetMapping("/cities/{cityName}")
    fun getForecastByCity(@PathVariable cityName: String): List<String> {
        return forecastService.getForecastByCity(cityName)
    }
}
