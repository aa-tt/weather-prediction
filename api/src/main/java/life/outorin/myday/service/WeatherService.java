package life.outorin.myday.service;

import com.datastax.astra.client.Database;
import com.datastax.astra.client.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import life.outorin.myday.dto.Coord;
import life.outorin.myday.dto.WeatherReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    @Value("${kafka.topic.report}")
    private String reportTopic;
    @Value("${kafka.topic.data}")
    private String dataTopic;

    @Autowired
    private ConnectorService connectorService;

    @Autowired
    private Database database;

    @Autowired
    private ReportService reportService;

    @Autowired
    private KafkaProducerService producerService;

    @Autowired
    private ObjectMapper objectMapper;

    public Mono<WeatherReport> getReport(String city) {
        listCollections();
        return connectorService.getCity(city)
                .flatMap(coords -> {
                    if (coords.isEmpty()) {
                        return Mono.error(new RuntimeException("City not found"));
                    }
                    Coord coord = coords.get(0); // Assuming the first element is needed
                    return connectorService.getWeather(coord)
                    .doOnNext(weatherData -> {
                        try {
                            String weatherDataJson = objectMapper.writeValueAsString(weatherData);
                            producerService.produce(dataTopic, city, weatherDataJson);
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to convert weather data to JSON", e);
                        }
                    });
                })
//                .then(Mono.defer(() -> fetchWeatherReportFromCassandra(city)))
                .map(reportService::generateReport)
//                .flatMap(report -> saveWeatherReport(city, report))
                ;
    }

    public void listCollections() {
        System.out.println("Connected to AstraDB " + database.listCollections().toList());
        System.out.println("Connected to AstraDB " + database.getNamespaceName());
    }

//    private Mono<WeatherReport> saveWeatherReport(String city, WeatherReport report) {
//        return Mono.fromCallable(() -> {
//            Document document = Document.create(report);
//            document.put("_id", UUID.randomUUID().toString()); // Generate a unique _id
//            document.put("city", city); // Add city as primary key
//            document.put("timestamp", report.dt().atZone(ZoneId.systemDefault()).toInstant()); // Add timestamp as primary key
//            document.put("temp_min", report.temp_min()); // Add timestamp as primary key
//            document.put("temp_max", report.temp_max()); // Add timestamp as primary key
//            database.getCollection("weather_report").insertOne(document);
//
//            // Send the WeatherReport to the Kafka topic
//            producerService.produce(reportTopic, city, report.toString());
//
//            return report;
//        });
//    }

    private Mono<WeatherReport> fetchWeatherReportFromCassandra(String city) {
        return Mono.fromCallable(() -> {
            FindOneOptions options = new FindOneOptions().sort(Sorts.descending("timestamp"));
            Document document = database.getCollection("weather_report")
                    .findOne(Filters.eq("city", city), options)
                    .get();
            if (document == null) {
                throw new RuntimeException("Weather report not found for city: " + city);
            }
            System.out.println("Weather report found: " + document);
            return new WeatherReport(document);
        });
    }

    public Mono<List<WeatherReport>> fetchWeatherReportHistoryFromCassandra(String city) {
        return Mono.fromCallable(() -> {
            FindOptions options = new FindOptions().sort(Sorts.descending("timestamp"));
            List<Document> documents = database.getCollection("weather_report")
                    .find(Filters.eq("city", city), options)
                    .all();
            if (documents.isEmpty()) {
                throw new RuntimeException("Weather report not found for city: " + city);
            }
            return documents.stream()
                    .map(WeatherReport::new)
                    .collect(Collectors.toList());
        });
    }
}