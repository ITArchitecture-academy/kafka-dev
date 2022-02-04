package example;

import example.common.JSONSerdes;
import example.common.SlidingAverage;
import example.common.TemperatureReading;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.WindowStore;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class TemperatureMonitor {
    public static void main(final String[] args) {
        final Properties settings = new Properties();
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "temperature-monitor");
        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        settings.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        settings.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JSONSerdes.TemperatureReadingSerde.class);

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

        KStream<String, TemperatureReading> temperatures = builder.stream("temperature_readings");

        // todo Als erstes möchten wir alle Temperaturen die >10°C oder <2°C in ein Topic "temperature_alerts" schreiben

        // Wir möchten nun ein Sliding Window über die letzten 10s haben, welches jede Sekunde verschoben wird.
        // Als Grace Period ist 1s auch ok
        TimeWindows hoppingWindow = null;

        // Erstmal alle Temperatur-Messwerte gruppieren
        KGroupedStream<String, TemperatureReading> temperaturesBySensor = null;

        // nun möchten wir unsere Messwerte pro Sensor window'en
        TimeWindowedKStream<String, TemperatureReading> windowedTemperatures = null;

        // Nun ist es Zeit für eine eigene Aggregation. Wir möchten über die Windows jeweils den Durchschnittswert bilden.
        // Da es nicht möglich ist das gesamte Zeitfenster auf einmal zu konsumieren und zu summieren und durch die Anzahl
        // der Einträge zu teilen, müssen wir uns was einfallen lassen.
        // Tipp:Nutze die Klasse `SlidingAverage`
        // Tipp: Die Funktion .aggregate nimmt zwei Argumente:
        //  1. eine funktion, die den Initial-Wert erstellt (Tipp new SlidingAverage(0,0,0);)
        //  2. eine funktion (key, currentMessage, aggregate) -> newAggregate die das neue Aggregat berechnet

        KTable<Windowed<String>, SlidingAverage> slidingAverages = null;

        // Normalerweise immitiert ein Aggregat für jede Änderung ein Ereignis, aber wir wollen nur einen Wert pro Zeitfenster.
        //Das können wir mit suppress() erreichen
        KTable<Windowed<String>, SlidingAverage> suppressedSlidingAverage = null;


        // Jetzt nur noch die Durchschnittswerte bekommen und printen.
        KTable<Windowed<String>, Double> averages = null;
        averages.toStream().print(Printed.toSysOut());

        return builder.build();
    }
}
