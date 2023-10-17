package example.simple;

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
        // We have to assign a transactional ID so that Kafka knows who is responsible for the transaction.
        // This must be one-to-one! One ID per producer!
        // props.put(??, ??);

        final String CREDITS_TOPIC = "bank-credits";
        final String DEBITS_TOPIC = "bank-debits";

        int numMessages = 50;
        if (args.length == 1) {
            numMessages = Integer.parseInt(args[0]);
        }


        // Here we get a list of transactions (.limits the length of the stream).
        // Some of them are treacherous
        final List<BankTransaction> toGreet = Stream.generate(new BankTransactionSupplier(10000)).limit(numMessages).collect(Collectors.toList());

        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            // Before we can use transactions, we need to initialize them
            // todo

            for (BankTransaction transaction : toGreet) {
                // Let's start the transaction
                // How?

                // We simply append "<- Suspicious!" to a transaction value to better see when such transactions come through.
                String suspicious = transaction.isSuspicious ? " <-Suspicious!" : "";
                ProducerRecord<String, String> senderRecord = new ProducerRecord<>(CREDITS_TOPIC, transaction.sender_account, transaction.amount + "€" + suspicious);
                ProducerRecord<String, String> receiverRecord = new ProducerRecord<>(DEBITS_TOPIC, transaction.receiver_account, transaction.amount + "€" + suspicious);

                // Let's send the records
                producer.send(senderRecord);
                producer.send(receiverRecord);
                producer.flush();

                // For illustrative purposes only:

                if (transaction.isSuspicious) {
                    System.out.println("Suspicious transaction between " + transaction.sender_account + " and " + transaction.receiver_account + "! Amount: " + transaction.amount);
                    // todo cancel transaction
                } else {
                    // Todo commit transactions
                }
            }
            // That's not quite true ;)
            System.out.println("Produced " + numMessages + " Messages");
        }
    }
}
