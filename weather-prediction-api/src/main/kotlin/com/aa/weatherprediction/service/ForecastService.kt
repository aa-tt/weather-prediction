package com.aa.weatherprediction.service

import com.aa.weatherprediction.model.*
import org.springframework.messaging.Message
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.collections.ArrayList

@Service
class ForecastService(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    private val connectorService: ConnectorService
) {

    @Value("\${kafka.topic}")
    private val topic: String = ""

    fun getForecastByCity(name: String): Report {
        sendEvent(EVENT.ORDER)
        val city = connectorService.getCity(name).first()
        val weatherData: WeatherData = connectorService.getWeather(city.lat!!, city.lon!!)
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
        sendEvent(EVENT.SUCCESS)
        return Report(
            alerts = weatherAlerts,
            mains = weatherData.weather?.map { it.main!! },
            temp_min = weatherData.main?.temp_min,
            temp_max = weatherData.main?.temp_max,
        )
    }

    private fun sendEvent(type: EVENT) {
        val eventMessage: Message<UsagePrice> = MessageBuilder
            .withPayload(UsagePrice("User1", type))
            .setHeader(KafkaHeaders.TOPIC, topic)
            .setHeader("X-Custom-Header", "Custom header here")
            .build()
        kafkaTemplate.send(eventMessage)
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
