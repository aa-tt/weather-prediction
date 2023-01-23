package com.aa.weatherprediction.model

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class Report (
    var alerts: List<String> = listOf(),
    var temp_min: Double? = null,
    var temp_max: Double? = null,
)
