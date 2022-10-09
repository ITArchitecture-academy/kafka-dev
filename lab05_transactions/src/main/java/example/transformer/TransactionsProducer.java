package example.transformer;

import example.common.BankTransaction;
import example.common.BankTransactionSerializer;
import example.common.BankTransactionSupplier;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.stream.Stream;

public class TransactionsProducer {
    public static void main(final String[] args) {
        final Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, BankTransactionSerializer.class);
        final String TOPIC = "bank-transactions";

        int msgsPerSec = 1;
        if (args.length == 1) {
            msgsPerSec = Integer.parseInt(args[0]);
        }

        final Stream<BankTransaction> toGreet = Stream.generate(new BankTransactionSupplier(msgsPerSec));

        try (Producer<String, BankTransaction> producer = new KafkaProducer<>(props)) {
            toGreet.forEach(bankTransaction -> {
                ProducerRecord<String, BankTransaction> producerRecord = new ProducerRecord<>(TOPIC, bankTransaction);
                producer.send(producerRecord);
                System.out.println("Produced record" + bankTransaction);
            });
        }
    }
}
