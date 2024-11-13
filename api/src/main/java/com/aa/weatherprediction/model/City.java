package com.aa.weatherprediction.model;

import com.squareup.moshi.JsonClass;
import kotlinx.serialization.Serializable;

@JsonClass(generateAdapter = true)
@Serializable
public record City(
        String name,
        Double lat,
        Double lon,
        String country,
        String state
) {
    public City() {
        this(null, null, null, null, null);
    }
}
