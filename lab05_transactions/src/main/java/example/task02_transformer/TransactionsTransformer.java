package example.task02_transformer;

import example.common.BankTransaction;
import example.common.BankTransactionDeserializer;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class TransactionsTransformer {
    public static void main(final String[] args) {

        // Wir brauchen nun sowohl einen Consumer als auch einen Producer
        final Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, BankTransactionDeserializer.class);
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "transactions-transformer");
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // todo Wir möchten Offsets innerhalb der Transaktion committen. Dafür müssen wir das auto-commit ausschalten!

        final Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // todo denk an die transactional-id!

        // unsere Topics
        final String TRANSACTIONS_TOPIC = "bank-transactions";
        final String CREDITS_TOPIC = "bank-credits";
        final String DEBITS_TOPIC = "bank-debits";

        // Und Consumer/Producer
        final Consumer<String, BankTransaction> consumer = new KafkaConsumer<>(consumerProps);
        final Producer<String, String> producer = new KafkaProducer<String, String>(producerProps);

        // RAII
        try (consumer) {
            try (producer) {
                // todo initialisiere die Transaktion und subscribe das Eingabe-Topic

                // todo: Wir benötigen die Metadaten der Consumer group um unsere Offsets zu committen
                ConsumerGroupMetadata consumerGroupMetadata = null;

                while (true) {
                    ConsumerRecords<String, BankTransaction> records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, BankTransaction> record : records) {
                        BankTransaction transaction = record.value();
                        // todo starte die Transaktion

                        // Das kennst du aus Teil 1
                        String suspicious = transaction.isSuspicious ? " <-Suspicious!" : "";
                        ProducerRecord<String, String> senderRecord = new ProducerRecord<>(CREDITS_TOPIC, transaction.sender_account, transaction.amount + "€" + suspicious);
                        ProducerRecord<String, String> receiverRecord = new ProducerRecord<>(DEBITS_TOPIC, transaction.receiver_account, transaction.amount + "€" + suspicious);
                        System.out.println("Transforming " + suspicious + " transaction " + transaction);

                        // Wir senden die Nachrichten
                        producer.send(senderRecord);
                        producer.send(receiverRecord);

                        //Und jetzt geht es darum die Offsets zu committen
                        // Unser Consumer weiß nichts von der Transaktion! Deshalb kümmert sich unser Producer darum die Offsets korrekt zu committen!
                        // Dafür müssen wir sagen für welche Partition welches Offset geschrieben werden soll.
                        // Tipps:
                        // * new TopicPartition()
                        // * Der Record kennt seinen aktuellen Offset, das Topic und die Partition!
                        // * Wir committen immer den nächsten Offset den wir lesen möchten, nicht den Offset des zuletzt gelesenen Eintrags!

                        Map<TopicPartition, OffsetAndMetadata> offsets =null; // todo
                        producer.sendOffsetsToTransaction(offsets, consumerGroupMetadata);

                        // Wenn die Transaktion verdächtig ist: Abbrechen! Sonst Committen.
                        if (transaction.isSuspicious) {
                            System.out.println("Suspicious transaction between " + transaction.sender_account + " and " + transaction.receiver_account + "! Amount: " + transaction.amount);
                            // todo
                        } else {
                            // todo
                        }
                    }
                }
            }
        }
    }
}
