package example.task02_international;

import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;
import java.util.stream.Stream;

public class HelloInternational {
    public static void main(final String[] args) {
        final Properties props = new Properties();
        // Welche Einstellungen sind hier relevant
        // props.put(??, ??);
        // Bitte beachte, wir möchten nun keine Strings, sondern InternationalGreeting's produzieren.
        // Glücklicherweise steht uns schon eine Serialisierungs-Klasse bereit
        final String TOPIC = "greetings_international";

        // Per CLI-Argument Anzahl der Nachrichten pro Sekunde definieren
        int msgsPerSec = 1;
        if (args.length == 1) {
            msgsPerSec = Integer.parseInt(args[0]);
        }

        // Wir bekommen die Daten nun nicht als Liste, sondern als Java-Stream
        final Stream<InternationalGreeting> toGreet = Stream.generate(new GreetingSupplier(msgsPerSec));

        // Wie sieht nun unser Producer aus?
        // Producer<??, ??> producer = new ??
        Producer producer = null;
        try (producer) {
            // Keine For-Schleife, sondern forEach -> es ist ja ein Stream
            toGreet.forEach(greeting -> {
                // Irgendwas müssen wir hier produzieren. Wie und was?
                // Tipp: da sich die Kafka-Bibliothek um das serialisieren kümmert, müssen wir es nicht selbst machen :)
                //producer.send(??);
                // Damit wir auch sehen, dass was passiert
                System.out.println("Produced greeting for receiver " + greeting.receiver_name);
            });
        }
    }
}
