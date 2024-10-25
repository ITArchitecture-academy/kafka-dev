package wind;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import wind.extra.HeavyProducerPartitioner;

import java.util.Properties;
import java.util.stream.Stream;

public class WindTurbineProducer {
    public static void main(final String[] args) {
        final Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSchemaSerializer.class);
        props.put(KafkaJsonSchemaSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://127.0.0.1:8081");
        props.put(KafkaJsonSchemaSerializerConfig.AUTO_REGISTER_SCHEMAS, false);
        props.put(KafkaJsonSchemaSerializerConfig.FAIL_INVALID_SCHEMA, true);
        props.put(KafkaJsonSchemaSerializerConfig.USE_SCHEMA_ID, 1);

        final String TOPIC = "wind-turbine-data";

        // The WindTurbineDataSupplier creates a Stream of approximately `msgsPerSec`
        // messages per seconds for you to produce
        int msgsPerSec = 1;
        if (args.length == 1) {
            msgsPerSec = Integer.parseInt(args[0]);
        }
        final Stream<WindTurbineData> windTurbineDataStream = Stream
                .generate(new WindTurbineDataSupplier(50, msgsPerSec));

        Producer<String, WindTurbineData> producer = new KafkaProducer<>(props);

        try (producer) {
            windTurbineDataStream.forEach(turbineData -> {
                ProducerRecord<String, WindTurbineData> record = new ProducerRecord<>(TOPIC, turbineData.windTurbineId, turbineData);

                producer.send(record);
                System.out.println("Produced data for wind turbine " + turbineData.windTurbineId);
            });
        }
    }
}
