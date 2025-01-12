package life.outorin.myday.service;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void produce(String topic, String key, String value) {
        kafkaTemplate.send(new ProducerRecord<>(topic, key, value));
        System.out.println(
            String.format(
                "Produced message to topic %s: key = %s value = %s", topic, key, value
            )
        );
    }
}
