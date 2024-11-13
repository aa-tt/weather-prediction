package com.aa.weatherprediction.model;

import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true)
public record DayAndReport(
        String day,
        Report report
) {
    public DayAndReport() {
        this(null, null);
    }
}
