package life.outorin.myday.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.EnableKafka;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, String> producerFactory() throws IOException {
        Map<String, Object> configProps = new HashMap<>();
        Properties properties = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("kafka-client.properties")) {
            if (input == null) {
                throw new IOException("Unable to find kafka-client.properties");
            }
            properties.load(input);
        }

        for (String key : properties.stringPropertyNames()) {
            configProps.put(key, properties.getProperty(key));
        }

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() throws IOException {
        return new KafkaTemplate<>(producerFactory());
    }
}