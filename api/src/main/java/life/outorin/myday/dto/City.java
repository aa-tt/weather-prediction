package life.outorin.myday.dto;

import java.io.Serializable;

public record City (
        String name,
        Double lat,
        Double lon,
        String country,
        String state
) implements Serializable {
}