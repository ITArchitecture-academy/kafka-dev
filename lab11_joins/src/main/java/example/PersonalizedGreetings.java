package example;

import example.common.InternationalGreeting;
import example.common.NameAndLanguage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class PersonalizedGreetings {
    public static void main(final String[] args) {

        final Properties settings = new Properties();
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "personalized-greetings-app");
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

        // | name | language |
        // Users wird ziemlich groß. Wir möchten deshalb eine KTable nutzen
        // Den Source-Processor nennen wir `users-source`
        final KTable<String, String> users = null;

        // | language | greeting
        // Die Tabelle languages bleibt klein und um die Performance zu verbessern, nutzen wir hier eine GlobalKTable
        final GlobalKTable<String, String> languages = null;

        // Der Stream names hat keinen Key. Aber wir möchten diesen Stream joinen. Das heißt wir brauchen einen Key.
        final KStream<String, String> names = null;

        // nun Joinen wir Namen und Sprachen. Nutze die Klasse NameAndLanguage als Value
        final KStream<String, NameAndLanguage> nameAndLanguages = null;

        // Und zeigen das an
        nameAndLanguages.print(Printed.toSysOut());

        // Nun möchten wir unsere Namen mit Sprache und die Greetings joinen um die Nutzer persönlich grüßen zu können

        // Für komplexe Joins ist es einfacher die KeyValueMapper und ValueJoiner aus den Parametern rauszunehmen.
        KeyValueMapper<String, NameAndLanguage, String> keySelector = null;
        ValueJoiner<NameAndLanguage, String, InternationalGreeting> valueJoiner = null;
        final KStream<String,InternationalGreeting> personalizedGreetings = nameAndLanguages.join(languages,
                keySelector,
                valueJoiner);

        // Ab auf die Kommandozeile ;)
        personalizedGreetings.print(Printed.toSysOut());


        return builder.build();
    }
}
