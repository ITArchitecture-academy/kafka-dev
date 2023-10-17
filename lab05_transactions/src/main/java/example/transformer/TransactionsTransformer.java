package example.transformer;

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
import java.util.Map;
import java.util.Properties;

public class TransactionsTransformer {
    public static void main(final String[] args) {

        // We now need both a consumer and a producer
        final Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, BankTransactionDeserializer.class);
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "transactions-transformer");
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // todo We want to commit offsets within the transaction. For this we have to switch off the auto-commit!

        final Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // todo remember the transactional-id!

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
                // todo initialize the transaction and subscribe to the input topic.

                // todo: we need the metadata of the consumer group to commit our offsets
                ConsumerGroupMetadata consumerGroupMetadata = null;

                while (true) {
                    ConsumerRecords<String, BankTransaction> records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, BankTransaction> record : records) {
                        BankTransaction transaction = record.value();
                        // todo start the transaction

                        // You know this from part 1
                        String suspicious = transaction.isSuspicious ? " <-Suspicious!" : "";
                        ProducerRecord<String, String> senderRecord = new ProducerRecord<>(CREDITS_TOPIC, transaction.sender_account, transaction.amount + "€" + suspicious);
                        ProducerRecord<String, String> receiverRecord = new ProducerRecord<>(DEBITS_TOPIC, transaction.receiver_account, transaction.amount + "€" + suspicious);
                        System.out.println("Transforming " + suspicious + " transaction " + transaction);

                        // We send the messages
                        producer.send(senderRecord);
                        producer.send(receiverRecord);
                        producer.flush();

                        // And now it's about committing the offsets
                        // Our consumer does not know anything about the transaction! Therefore our producer takes care of committing the offsets correctly!
                        // For this we have to say for which partition which offset should be written.
                        // Tips:
                        // * new TopicPartition()
                        // * The record knows its current offset, topic and partition!
                        // * We always commit the next offset we want to read, not the offset of the last read record!

                        Map<TopicPartition, OffsetAndMetadata> offsets =null; // todo
                        producer.sendOffsetsToTransaction(offsets, consumerGroupMetadata);

                        // If the transaction is suspicious: Cancel! Otherwise commit.
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
