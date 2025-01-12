package life.outorin.myday.dto;

import java.io.Serializable;
import java.util.List;

public record WeatherData(
        Coord coord,
        Wind wind,
        Main main,
        Rain rain,
        List<Weather> weather,
        int dt
) implements Serializable {
}
