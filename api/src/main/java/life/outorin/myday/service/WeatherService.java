package life.outorin.myday.service;

import com.datastax.astra.client.Database;
import com.datastax.astra.client.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import life.outorin.myday.dto.Coord;
import life.outorin.myday.dto.WeatherReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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

    private Mono<WeatherReport> fetchCurrentWeatherReportFromCassandra(String city) {
        return Mono.fromCallable(() -> {
            LocalDateTime currentTime = LocalDate.now().atStartOfDay();
            FindOptions options = new FindOptions().sort(Sorts.descending("timestamp"));
            List<Document> document = database.getCollection("weather_report")
                    .find(Filters.eq("city", city), options).all();
            System.out.println("Weather report found: " + document);
            return new WeatherReport(document.stream().findFirst().get());
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
        })
        .retryWhen(Retry.backoff(10, Duration.ofSeconds(1))
        .maxBackoff(Duration.ofSeconds(10))
        .jitter(0.5)
        .doBeforeRetry(retrySignal -> 
            System.out.println("Retrying due to: " + retrySignal.failure().getMessage())
        ));
    }

    public SseEmitter getCurrentWeatherAlerts(String city) {
        SseEmitter emitter = new SseEmitter();

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
//            if (!isEmitterActive(emitter)) {
                fetchCurrentWeatherReportFromCassandra(city)
                        .doOnNext(weatherReport -> {
                            try {
                                emitter.send(SseEmitter.event().data(weatherReport.alerts()));
                            } catch (IOException e) {
                                emitter.completeWithError(e);
                            }
                        })
                        .doOnError(error -> {
                            try {
                                emitter.send(SseEmitter.event().data(List.of()));
                            } catch (IOException e) {
                                emitter.completeWithError(e);
                            }
                        })
                        .subscribe();
//            }   else {
//                executor.shutdown();
//            }

        }, 0, 60, TimeUnit.SECONDS); // Check every 5 seconds

        emitter.onCompletion(executor::shutdown);
        emitter.onTimeout(executor::shutdown);

        return emitter;
    }

    private boolean isEmitterActive(SseEmitter emitter) {
        try {
            emitter.send(SseEmitter.event().comment(""));
            return true;
        } catch (IOException e) {
            return false;
        }
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
}