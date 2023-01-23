package com.aa.weatherprediction.model

import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@JsonClass(generateAdapter = true)
@Serializable
data class City(
    var name: String?,
    var lat: Double?,
    var lon: Double?,
    var country: String?,
    var state: String?,
) {
    constructor() : this(null, null, null, null, null)
}
