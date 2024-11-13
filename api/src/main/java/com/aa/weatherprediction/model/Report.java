package com.aa.weatherprediction.model;

import com.squareup.moshi.JsonClass;

import java.util.List;

@JsonClass(generateAdapter = true)
public record Report(
        List<String> alerts,
        List<String> mains,
        Double temp_min,
        Double temp_max
) {
    public Report() {
        this(List.of(), List.of(), null, null);
    }
}
