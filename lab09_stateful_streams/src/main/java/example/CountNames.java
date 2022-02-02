package example;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class CountNames {
    // Nichts neues hier
    public static void main(final String[] args) {

        final Properties settings = new Properties();
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "count-names-app");
        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        settings.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        settings.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());


        final Topology topology = getTopology();
        System.out.println("you can paste the topology into this site for a vizualization: https://zz85.github.io/kafka-streams-viz/");
        System.out.println(topology.describe());
        final KafkaStreams streams = new KafkaStreams(topology, settings);
        final CountDownLatch latch = new CountDownLatch(1);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("<<< Stopping the streams-app Application");
            streams.close();
            latch.countDown();
        }));

        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }

    private static Topology getTopology() {
        StreamsBuilder builder = new StreamsBuilder();
        final KStream<String, String> greetings = null;
        // Wir müssen das "Hello " am Anfang loswerden
        final KStream<String, String> names = null;
        // Um zu zählen, müssen wir zuerst nach etwas gruppieren (wir zählen nie alles).
        // Oft nutzen wir dafür einfach groupByKey. Aber hier wollen wir nach dem Namen gruppieren, und der ist unser Value
        // Welche Möglichkeiten haben wir dafür?
        final KGroupedStream<String, String> groupedStream = null;
        // Nun zählen wir das
        final KTable<String, Long> counts = null;
        // Und schreiben das Ergebnis in das Topic `greeting_names_count`
        // Tipp: Wir können Tabellen nicht direkt in ein Topic schreiben. Zuerst müssen wir dies in einen Stream umwandeln
        //counts.??
        
        return builder.build();
    }

}
