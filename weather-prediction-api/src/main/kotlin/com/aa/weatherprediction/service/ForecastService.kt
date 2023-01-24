package com.aa.weatherprediction.service

import com.aa.weatherprediction.model.City
import com.aa.weatherprediction.model.DayAndReport
import com.aa.weatherprediction.model.Report
import com.aa.weatherprediction.model.WeatherData
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

@Service
class ForecastService(
    private val connectorService: ConnectorService
) {

    fun getForecastByCity(name: String): Report {
        val city: Optional<City> = connectorService.getCity(name)
        if(city.isEmpty) {
            return Report();
        }
        val weatherData: WeatherData = connectorService.getWeather(city.get().lat!!, city.get().lon!!)
        val weatherAlerts: ArrayList<String> = arrayListOf()
        if (weatherData.wind?.speed!! > 10) {
            weatherAlerts.add("It’s too windy, watch out!")
        }
        if (weatherData.main?.temp!! > 40) {
            weatherAlerts.add("Use sunscreen lotion")
        }
        if (weatherData.rain != null) {
            weatherAlerts.add("Carry Umbrella")
        }
        return Report(
            alerts = weatherAlerts,
            mains = weatherData.weather?.map { it.main!! },
            temp_min = weatherData.main?.temp_min,
            temp_max = weatherData.main?.temp_max,
        )
    }

    // using Mock Data as forecast service comes under Pro subscription
    fun getForecastByDays(name: String, days: Long): ArrayList<DayAndReport> {
        var reports = arrayListOf<DayAndReport>()
        for(day in 1..days) {
            reports.add(
                DayAndReport(
                    day = LocalDate.now().plusDays(day).dayOfWeek.name,
                    report = getForecastByCity(name)
                )
            )
        }
        return reports
    }
}
