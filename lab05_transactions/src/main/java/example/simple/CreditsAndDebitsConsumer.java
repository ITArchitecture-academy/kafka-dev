package example.simple;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Properties;

public class CreditsAndDebitsConsumer {
    public static void main(final String[] args) {
        final Properties props = new Properties();
                props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "credits-and-debits-consumer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // todo Something is missing here - we see too many messages ;)

        final Consumer<String, String> consumer = new KafkaConsumer<>(props);
        final String CREDITS_TOPIC = "bank-credits";
        final String DEBITS_TOPIC = "bank-debits";


        try (consumer) {
            // How do we actually consume from multiple Topics?
            // todo

            System.out.println("Started");
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String key = record.key();
                    String value = record.value();
                    System.out.println(record.topic() + " " + key + ": " + value);
                }
            }
        }
    }
}
