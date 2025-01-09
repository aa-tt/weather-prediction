package life.outorin.myday.service;

import life.outorin.myday.dto.Coord;
import life.outorin.myday.dto.WeatherReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class WeatherService {

    @Autowired
    private ConnectorService connectorService;

    @Autowired
    private ReportService reportService;

    public Mono<WeatherReport> getReport(String city) {
        return connectorService.getCity(city)
                .flatMap(coords -> {
                    if (coords.isEmpty()) {
                        return Mono.error(new RuntimeException("City not found"));
                    }
                    Coord coord = coords.get(0); // Assuming the first element is needed
                    return connectorService.getWeather(coord);
                })
                .map(reportService::generateReport);
    }
}