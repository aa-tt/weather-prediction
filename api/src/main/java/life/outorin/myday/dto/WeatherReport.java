package life.outorin.myday.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public record WeatherReport(
        List<String> alerts,
        List<String> mains,
        Double temp_min,
        Double temp_max,
        LocalDateTime dt
) {
    public static LocalDateTime convertToDateTime(int epochSeconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSeconds), ZoneId.systemDefault());
    }
}
