package example;

import example.common.JSONSerdes;
import example.common.TemperatureReading;
import example.common.TemperatureReadingSupplier;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

public class TemperatureReadingProducer {
    public static void main(final String[] args) {
        final Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, (new JSONSerdes.TemperatureReadingSerde()).serializer().getClass());
        final String TOPIC = "temperature_readings";

        int msgsPerSec = 1;
        if (args.length == 1) {
            msgsPerSec = Integer.parseInt(args[0]);
        }

        final Stream<Map.Entry<String, TemperatureReading>> toGreet = Stream.generate(new TemperatureReadingSupplier(msgsPerSec));

        try (Producer<String, TemperatureReading> producer = new KafkaProducer<>(props)) {
            toGreet.forEach(reading -> {
                ProducerRecord<String, TemperatureReading> producerRecord = new ProducerRecord<>(TOPIC, reading.getKey(), reading.getValue());
                producer.send(producerRecord);
                System.out.println("Produced reading for sensor " + reading.getKey());
            });
        }
    }
}
