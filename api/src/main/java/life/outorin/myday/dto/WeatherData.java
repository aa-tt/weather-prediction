package life.outorin.myday.dto;

import java.util.List;

public record WeatherData(
        Coord coord,
        Wind wind,
        Main main,
        Rain rain,
        List<Weather> weather,
        int dt
) {
}
