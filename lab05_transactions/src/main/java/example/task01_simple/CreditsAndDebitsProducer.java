package example.task01_simple;

import example.common.BankTransaction;
import example.common.BankTransactionSupplier;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreditsAndDebitsProducer {
    public static void main(final String[] args) {
        final Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // Wir müssen eine Transactional ID vergeben damit Kafka weiß, wer für die Transaktion verantwortlich ist.
        // Diese muss Ein-Eindeutig sein! Pro Producer eine ID!
        // props.put(??, ??);

        final String CREDITS_TOPIC = "bank-credits";
        final String DEBITS_TOPIC = "bank-debits";

        int numMessages = 50;
        if (args.length == 1) {
            numMessages = Integer.parseInt(args[0]);
        }


        // Hier bekommen wir eine Liste von Transaktionen (.limit limitiert die länge des streams).
        // Einige davon sind verräterisch
        final List<BankTransaction> toGreet = Stream.generate(new BankTransactionSupplier(10000)).limit(numMessages).collect(Collectors.toList());

        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            // Bevor wir Transaktionen benutzen können, müssen wir sie initialisieren
            // todo

            for (BankTransaction transaction : toGreet) {
                // Starten wir die Transaktion
                // Wie?

                // Wir hängen einfach "<- Suspicious!" an eine Transaktionswert an um besser zu sehen wenn solche Transaktionen durchkommen
                String suspicious = transaction.isSuspicious ? " <-Suspicious!" : "";
                ProducerRecord<String, String> senderRecord = new ProducerRecord<>(CREDITS_TOPIC, transaction.sender_account, transaction.amount + "€" + suspicious);
                ProducerRecord<String, String> receiverRecord = new ProducerRecord<>(DEBITS_TOPIC, transaction.receiver_account, transaction.amount + "€" + suspicious);

                // Senden wir die Records
                producer.send(senderRecord);
                producer.send(receiverRecord);

                // Nur für die Anschaulichkeit:

                if (transaction.isSuspicious) {
                    System.out.println("Suspicious transaction between " + transaction.sender_account + " and " + transaction.receiver_account + "! Amount: " + transaction.amount);
                    // todo Transaktion abbrechen
                } else {
                    // Todo Transaktion committen
                }
            }
            // Das stimmt so nicht ganz ;)
            System.out.println("Produced " + numMessages + " Messages");
        }
    }
}
