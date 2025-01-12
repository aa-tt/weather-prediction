package life.outorin.myday.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.datastax.astra.client.model.Document;

public record WeatherReport(
        List<String> alerts,
        List<String> mains,
        Double temp_min,
        Double temp_max,
        LocalDateTime dt
) {

    public WeatherReport(Document document) {
        this(
            document.getList("alerts", String.class),
            document.getList("mains", String.class),
            document.getDouble("temp_min"),
            document.getDouble("temp_max"),
            document.get("timestamp") != null ? convertToDateTime(document.getString("timestamp")) : null
        );
    }
    public static LocalDateTime convertToDateTime(int epochSeconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSeconds), ZoneId.systemDefault());
    }
    public static LocalDateTime convertToDateTime(String timestamp) {
        return LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_DATE_TIME);
    }
}
