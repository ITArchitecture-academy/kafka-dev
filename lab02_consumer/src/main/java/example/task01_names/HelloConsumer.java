package example.task01_names;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class HelloConsumer {
    public static void main(final String[] args) {
        final Properties props = new Properties();
        // Wir kennen das Spiel: Welche Konfigurationen benötigen wir?
        // Tipp: für Consumer gibt es die Klasse ConsumerConfig.
        // Tipp: Im Consumer serialisieren wir die Daten nicht. Wir Deserialisieren sie ;)

        // Auch neu: Wir müssen eine Group ID für unseren Consumer vorgeben. ACHTUNG: Diese sollte sich nicht mit anderen Consumer Groups überschneiden
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "falscher Wert!");
        // Fangen wir am Ende oder am Anfang einer Partition an zu lesen? Entscheide du!
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "falscher Wert!");

        final String TOPIC = "greetings";

        // Initialisiere den Consumer
        // final Consumer<??, ??> consumer = ??
        final Consumer consumer = null;


        // Auch hier müssen wir den Consumer selbst schließen oder es Javas RAII überlassen ;)
        try (consumer) {
            // Wir müssen irgendwie dem consumer sagen, aus welchem Topic dieser konsumieren soll
            // Tipp: irgendwas mit subscribe ;)

            System.out.println("Started");
            // Nutzen While-True Schleife um kontinuierlich Daten abzuholen
            while (true) {
                // Wie kommen wir an unsere Consumer Records
                ConsumerRecords<String, String> records = null;
                // Jetzt geben wir die Daten einfach aus
                for (ConsumerRecord<String, String> record : records) {
                    String key = record.key();
                    String value = record.value();
                    System.out.println(key + ": " + value);
                }
            }
        }
    }
}
