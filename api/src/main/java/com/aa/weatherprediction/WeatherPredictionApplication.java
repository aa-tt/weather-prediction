
package com.aa.weatherprediction;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WeatherPredictionApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherPredictionApplication.class, args);
    }
}