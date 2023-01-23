package com.aa.weatherprediction.service

import com.aa.weatherprediction.model.City
import com.aa.weatherprediction.model.Report
import com.aa.weatherprediction.model.WeatherData
import okio.use
import org.springframework.stereotype.Service
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
            temp_min = weatherData.main?.temp_min,
            temp_max = weatherData.main?.temp_max,
        )
    }
}
