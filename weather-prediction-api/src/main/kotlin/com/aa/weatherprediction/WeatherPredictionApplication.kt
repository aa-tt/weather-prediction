package com.aa.weatherprediction

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class WeatherPredictionApplication

fun main(args: Array<String>) {
	runApplication<WeatherPredictionApplication>(*args)
}
