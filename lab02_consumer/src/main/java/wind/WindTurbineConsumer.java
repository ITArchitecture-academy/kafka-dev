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
        // What do you need to configure here?
        // Hints:
        // Consumers use Deserializers instead of Serializers
        // Consumers need a group.id
        // Check the auto.offset.reset config ;)
        final String TOPIC = "wind-turbine-data";

        final Consumer consumer = null; // todo

        // We use this HashMap to share it with the Server to print it on request
        Map<String, WindTurbineData> measurements = new HashMap<>();
        // Create the API Server on Port 8989
        try (WindTurbineAPI api = new WindTurbineAPI(8989, measurements)) {
            try (consumer) {
                // Todo: How to define from which topics to consume?

                System.out.println("Started…");
                // In Kafka Consumers we use a infinite loop and fetch the new records in the loop
                while (true) {
                    // hint: ConsumerRecords consists of zero or many records
                    ConsumerRecords records = null; // todo (hint: do not forget the generic types signature)
                    // Please print all records (for your convenience) and add id to the `measurements`-HashMap using the following code:
                    // measurements.put(key, value);
                }
            }
        }
    }
}
