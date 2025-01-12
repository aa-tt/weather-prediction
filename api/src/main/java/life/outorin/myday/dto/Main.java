package life.outorin.myday.dto;

import java.io.Serializable;

public record Main(
        double temp,
        double temp_min,
        double temp_max) implements Serializable {

}
