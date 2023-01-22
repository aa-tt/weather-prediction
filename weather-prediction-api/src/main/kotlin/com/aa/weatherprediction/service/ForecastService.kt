package com.aa.weatherprediction.service

import com.aa.weatherprediction.model.City
import com.aa.weatherprediction.model.WeatherData
import okio.use
import org.springframework.stereotype.Service
import java.util.*

@Service
class ForecastService(
    private val connectorService: ConnectorService
) {

    fun getForecastByCity(name: String): List<String> {
        val city: Optional<City> = connectorService.getCity(name)
        if(city.isEmpty) {
            return listOf();
        }
        val weatherData = connectorService.getWeather(city.get().lat!!, city.get().lon!!)
        val weatherReport = arrayListOf<String>()
        if (weatherData.wind?.speed!! > 10) {
            weatherReport.add("It’s too windy, watch out!")
        }
        if (weatherData.main?.temp!! > 40) {
            weatherReport.add("Use sunscreen lotion")
        }
        if (weatherData.rain != null) {
            weatherReport.add("Carry Umbrella")
        }
        return weatherReport
    }
}
