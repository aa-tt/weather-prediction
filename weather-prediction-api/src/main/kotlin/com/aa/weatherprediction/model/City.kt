package com.aa.weatherprediction.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
    var name: String?,
    var lat: Double?,
    var lon: Double?,
    var country: String?,
    var state: String?,
)
