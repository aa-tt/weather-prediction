package com.aa.weatherprediction.resource

import com.aa.weatherprediction.model.DayAndReport
import com.aa.weatherprediction.model.Report
import com.aa.weatherprediction.service.ForecastService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController("/api/v1/forecast")
class ForecastResource(
    private val forecastService: ForecastService
) {

    @GetMapping("/cities/{cityName}/forecast", MediaType.APPLICATION_JSON_VALUE)
    fun getForecastByCity(@PathVariable cityName: String): ResponseEntity<Report> {
        return ResponseEntity(forecastService.getForecastByCity(cityName), HttpStatus.OK)
    }

    @GetMapping("/cities/{cityName}/days/{days}/forecast")
    fun getForecastByDays(
        @PathVariable cityName: String,
        @PathVariable(required = false) days: Long = 3,
    ): ResponseEntity<ArrayList<DayAndReport>> {
        return ResponseEntity(forecastService.getForecastByDays(cityName, days), HttpStatus.OK)
    }
}
