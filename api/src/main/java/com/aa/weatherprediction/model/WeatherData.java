package com.aa.weatherprediction.model;

import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true)
public record WeatherData(
        Coord coord,
        Wind wind,
        Main main,
        Rain rain,
        List<WeatherMain> weather,
        int dt
) {}

@JsonClass(generateAdapter = true)
record Coord(
        double lat,
        double lon
) {}

@JsonClass(generateAdapter = true)
record Wind(
        double speed
) {}

@JsonClass(generateAdapter = true)
record Main(
        double temp,
        double temp_min,
        double temp_max
) {}

@JsonClass(generateAdapter = true)
record Rain(
        Double lh
) {}

@JsonClass(generateAdapter = true)
record WeatherMain(
        String main
) {}
