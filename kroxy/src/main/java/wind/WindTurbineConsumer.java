package wind;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class WindTurbineConsumer {
    public static void main(final String[] args) throws IOException {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "wind-turbine-consumer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaJsonSchemaDeserializer.class);
        props.put(KafkaJsonSchemaDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://127.0.0.1:8081");
        props.put(KafkaJsonSchemaDeserializerConfig.AUTO_REGISTER_SCHEMAS, false);
        props.put(KafkaJsonSchemaDeserializerConfig.FAIL_INVALID_SCHEMA, true);
        props.put(KafkaJsonSchemaDeserializerConfig.USE_SCHEMA_ID, 1);
        props.put(KafkaJsonSchemaDeserializerConfig.JSON_VALUE_TYPE, WindTurbineData.class);
        props.put("json.oneof.for.nullables", false);
        final String TOPIC = "wind-turbine-data";

        final Consumer<String, WindTurbineData> consumer = new KafkaConsumer<>(props);

        try (consumer) {
            consumer.subscribe(List.of(TOPIC));

            System.out.println("Started…");
            while (true) {
                // hint: ConsumerRecords consists of zero or many records
                ConsumerRecords<String, WindTurbineData> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, WindTurbineData> record : records) {
                    System.out.println(record.key() + " " + record.value());
                }
            }
        }

    }
}
