package example;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class CountGreetings {
    public static void main(final String[] args) {

        final Properties settings = new Properties();
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "count-greetings");
        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // Todo Serdes korrekt konfigurieren
        // Tipp:Der Key ist der Name, das Value ist das InternationalGreeting


        // den Rest kennst du
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
        final KStream<String, InternationalGreeting> greetings = builder.stream("greetings_international");
        // Wir müssen den Stream so repartitionieren, dass der Name im Key ist
        final KStream<String, InternationalGreeting> byReceiverName = null;
        // Wir müssen den Stream so repartitionieren, dass die Sprache im Key ist
        final KStream<String, InternationalGreeting> byReceiverLanguage = null;

        // Nun möchten wir die beiden Streams nach Key Gruppieren und zählen. Dann schreiben wir sie in die entsprechenden Topics
        // byReceiverName.  ??? .toStream().to("greetings_count_by_receiver_name");
        // byReceiverLanguage.  ??? .toStream().to("greetings_count_by_receiver_language");

        return builder.build();
    }
}
