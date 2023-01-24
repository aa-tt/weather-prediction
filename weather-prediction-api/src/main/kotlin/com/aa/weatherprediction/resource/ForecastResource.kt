package com.aa.weatherprediction.resource

import com.aa.weatherprediction.model.DayAndReport
import com.aa.weatherprediction.model.Report
import com.aa.weatherprediction.service.ForecastService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController("/api/v1/forecast")
class ForecastResource(
    private val forecastService: ForecastService
) {

    @GetMapping("/cities/{cityName}/forecast")
    fun getForecastByCity(@PathVariable cityName: String): Report {
        return forecastService.getForecastByCity(cityName)
    }

    @GetMapping("/cities/{cityName}/days/{days}/forecast")
    fun getForecastByDays(
        @PathVariable cityName: String,
        @PathVariable(required = false) days: Long = 3,
    ): ArrayList<DayAndReport> {
        return forecastService.getForecastByDays(cityName, days)
    }
}
