package life.outorin.myday.dto;

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
