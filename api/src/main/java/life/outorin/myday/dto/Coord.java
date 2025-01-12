package life.outorin.myday.dto;

import java.io.Serializable;

public record Coord (
        double lat,
        double lon) implements Serializable {
}