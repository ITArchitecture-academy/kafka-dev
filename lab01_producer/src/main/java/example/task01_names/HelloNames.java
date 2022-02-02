package example.task01_names;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class HelloNames {
    public static void main(final String[] args) {
        final Properties props = new Properties();
        // Welche Konfigurationsoptionen sind wichtig?
        // props.put(ProducerConfig.???, ????);
        // Tipp: Wie verbinden wir uns mit Kafka (Port 9092 ;))
        // Tipp: Wie serialisieren wir unsere Daten?

        // In dieses Topic soll produziert werden
        final String TOPIC = "greetings";

        // Generiere X Nachrichten.
        int numMessages = 50;
        if(args.length == 1) { // So können wir per Kommandozeilenargumente die Anzahl der Nachrichten anpassen
            numMessages = Integer.parseInt(args[0]);
        }
        final List<String> toGreet = randomNames(numMessages); // <- hier sind die zu produzierenden Nachrichten

        // Erstelln wir einen Producer
        //Producer<??, ??> producer = new ??
        Producer producer = null;
        // Normalerweise müssen wir den Producer per close() immer schließen
        // In neueren Java-Versionen können wir `try(producer) {…}` benutzen.
        // Beim verlassen des try-Blocks wird der Producer automatisch geschlossen.
        try (producer) {
            for (String name : toGreet) {
                // Irgendwas müssen wir hier produzieren. Wie und was?
                //producer.send(??);
            }
            System.out.println("Produced " + numMessages + " Messages");
        }
    }

    // Implementierungsdetail ;)
    // Wir generieren hier lediglich einige Nachrichten, damit wir uns sie nicht ständig neu ausdenken müssen.
    private static List<String> randomNames(int numMessages) {
        String[] randomNames = new String[]{"Alice", "Bob", "Charlie", "Dave", "Eve", "Francis"};
        List<String> messages = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < numMessages; i++) {
            int randomIdx = rand.nextInt(randomNames.length);
            messages.add(randomNames[randomIdx]);
        }
        return messages;

    }
}
