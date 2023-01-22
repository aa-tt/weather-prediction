package com.aa.weatherprediction

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WeatherPredictionApplication

fun main(args: Array<String>) {
	runApplication<WeatherPredictionApplication>(*args)
}
