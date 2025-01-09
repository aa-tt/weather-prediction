package life.outorin.myday.repository;

import life.outorin.myday.dto.WeatherReport;
import java.util.concurrent.ExecutionException;

public interface WeatherDataRepository {
    void saveWeatherReport(String city, WeatherReport report) throws ExecutionException, InterruptedException;
}