package example.task02_international;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class GreetingSupplier implements Supplier<InternationalGreeting> {
    // Definieren wir einige Grußworte in unterschiedlichen Sprachen
    private final static List<Map.Entry<String, String>> LANGUAGE_GREETINGS = List.of(Map.entry("en", "Hello"),
            Map.entry("de", "Hallo"),
            Map.entry("ru", "Привет"),
            Map.entry("es", "Hola"));

    // Ein paar Beispielhafte Namen
    private final static List<String> RANDOM_NAMES = List.of("Alice", "Bob", "Charlie", "Dave", "Eve", "Francis");

    private final Random rand = new Random();

    // Konfiguration: Wie viele Nachrichten pro Sekunde soll der Supplier emmitieren?
    private final int msgsPerSec;
    // Interne Variable
    private int msgCount =0;

    public GreetingSupplier(int msgsPerSec) {
        this.msgsPerSec = msgsPerSec;
    }

    @Override
    public InternationalGreeting get() {
        // Generiere Zufällige Daten
        InternationalGreeting greeting = new InternationalGreeting();
        greeting.sender_name = RANDOM_NAMES.get(rand.nextInt(RANDOM_NAMES.size()));
        greeting.receiver_name = RANDOM_NAMES.get(rand.nextInt(RANDOM_NAMES.size()));

        Map.Entry<String, String> sender_language = LANGUAGE_GREETINGS.get(rand.nextInt(LANGUAGE_GREETINGS.size()));
        greeting.sender_language = sender_language.getKey();
        greeting.sender_greeting = sender_language.getValue() + " " + greeting.receiver_name;

        Map.Entry<String, String> receiver_language = LANGUAGE_GREETINGS.get(rand.nextInt(LANGUAGE_GREETINGS.size()));
        greeting.receiver_language = receiver_language.getKey();
        greeting.receiver_greeting = receiver_language.getValue() + " " + greeting.receiver_name;

        // Pausiere alle X Nachrichten für 1 Sekunde
        if (msgCount >= msgsPerSec && msgsPerSec != -1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            msgCount=0;
        }
        msgCount++;
        return greeting;
    }
}
