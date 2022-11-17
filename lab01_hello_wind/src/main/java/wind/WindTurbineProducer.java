package wind;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.stream.Stream;

public class WindTurbineProducer {
    public static void main(final String[] args) {
        final Properties props = new Properties();
        // These are the minimal properties needed

        //How to connect to Kafka?
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // How to serialize Keys?
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // How to serialize Values?
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, WindTurbineDataSerializer.class);

        final String TOPIC = "wind-turbine-data";

        // The WindTurbineDataSupplier creates a Stream of approximately `msgsPerSec` messages per seconds for you to produce
        int msgsPerSec = 1;
        if (args.length == 1) {
            msgsPerSec = Integer.parseInt(args[0]);
        }
        final Stream<WindTurbineData> windTurbineDataStream = Stream.generate(new WindTurbineDataSupplier(50, msgsPerSec));

        // initialize a producer
        // Please always close the producers. try(var) {} closes it automatically
        try (Producer<String, WindTurbineData> producer = new KafkaProducer<>(props)) {
            windTurbineDataStream.forEach(turbineData -> {
                String key = turbineData.windTurbineId;
                ProducerRecord<String, WindTurbineData> producerRecord = new ProducerRecord<>(TOPIC, key, turbineData);

                producer.send(producerRecord);
                System.out.println("Produced data for wind turbine " + turbineData.windTurbineId);
            });
        }
    }
}
