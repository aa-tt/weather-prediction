package com.aa.weatherprediction.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DayAndReport (
    var day: String,
    var report: Report,
)
