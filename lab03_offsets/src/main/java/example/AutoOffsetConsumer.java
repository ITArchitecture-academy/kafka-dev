package example;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class AutoOffsetConsumer {
    public static void main(final String[] args) {
        // What do you need to configure here?
        final Properties props = new Properties();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // How do we set how often offsets are committed?
        // props.put(??, ??);

        // You know the rest
        final String TOPIC = "wind-turbine-data";

        final Consumer<String, String> consumer = new KafkaConsumer<>(props);

        try (consumer) {
            consumer.subscribe(Collections.singletonList(TOPIC));

            System.out.println("Started");
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String key = record.key();
                    String value = record.value();
                    System.out.println(key + ": " + value);
                }
            }
        }
    }
}
