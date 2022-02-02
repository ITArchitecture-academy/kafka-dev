package example;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class HelloStreams {
    public static void main(final String[] args) {

        final Properties settings = new Properties();
        //settings.put(??,??)
        // Tipp: Du musst in Streams eine application id vergeben
        // Tipp: Wir haben keine Serializer/Deserializer sondern sogenannte Serdes in Streams. Wir arbeiten erstmal mit Strings
        //       Serdes.String().getClass() hilft ;)

        // Siehe weiter unten
        final Topology topology = getTopology();
        System.out.println("you can paste the topology into this site for a vizualization: https://zz85.github.io/kafka-streams-viz/");
        System.out.println(topology.describe());
        final KafkaStreams streams = new KafkaStreams(topology, settings);

        // Wir lassen unsere Streams-Applikation im Hintergrund laufen
        final CountDownLatch latch = new CountDownLatch(1);

        //... und warten auf ein SIGTERM. Dann schließen wir sauber Kafka Streams und beenden das Programm
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("<<< Stopping the streams-app Application");
            streams.close();
            latch.countDown();
        }));

        try {
            // Jetzt ist es Zeit Streams zu starten
            streams.start();
            latch.await();
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }

    private static Topology getTopology() {
        StreamsBuilder builder = new StreamsBuilder();
        // Wir benötigen einen sogenannten Source-Processor.
        // In Kafka Streams können wir uns per .stream() an ein Topic "anschließen"
        // Wir möchten die Daten aus dem Topic greetings lesen
        final KStream<String, String> greetings = null; // todo
        // und vom Englischen ins Spanische übersetzen. Also "Ola Alice" statt "Hello Alice"
        // Wir nutzen in Streams dafür unterschiedliche Processoren:
        //   filter((key, value) -> bool): Filtern
        //   mapValues(value -> value.toUpper()): Values verändern
        //   etc

        // todo: was wäre denn hier für unsere Übersetzung am sinnvollsten?
        final KStream<String, String> spanishGreetings = null;

        // So können wir Daten auf die Kommandozeile ausgeben
        spanishGreetings.print(Printed.toSysOut());
        // und so in ein Topic schreiben
        spanishGreetings.to("greetings_spanish");

        // Teil 2

        /*
        //Jetzt interessieren wir uns nur für die Spanischen Begrüßungen, die weniger als 2x den Buchstaben 'e' enthalten

        final KStream<String, String> fewEes = spanishGreetings.??;

        // mit split() können wir einen KStream in mehrere aufteilen. Wir möchten gerne alle Begrüßungen, die auf ein Vokal
        // enden (z.B. per Regex .matches(".*[aeou]$") ) in das topic `greeting_vowels` schreiben und die anderen in das Topic `greeting_consonants`
        //
        // Tipp: aktuell schreiben wir alle Daten in das Topic `greeting_consonants`
        fewEes.split()
                .defaultBranch(
                        Branched.withConsumer(stream -> stream.to("greetings_consonants")));
        */



        // Geben wir die Topologie zurück
        return builder.build();
    }

}
