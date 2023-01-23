package com.aa.weatherprediction.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherData(
    var coord: Coord?,
    var wind: Wind?,
    var main: Main?,
    var rain: Rain?,
    var dt: Int
) {constructor(): this(null,null,null,null,0)}

@JsonClass(generateAdapter = true)
data class Coord(
    var lat: Double,
    var lon: Double,
){constructor():this(0.0,0.0)}

@JsonClass(generateAdapter = true)
data class Wind(
    var speed: Double,
){constructor(): this(0.0)}

@JsonClass(generateAdapter = true)
data class Main(
    var temp: Double,
    var temp_min: Double,
    var temp_max: Double,
){constructor(): this(0.0,0.0,0.0)}

@JsonClass(generateAdapter = true)
data class Rain(
    var lh: Double?,
){constructor(): this(null)}
