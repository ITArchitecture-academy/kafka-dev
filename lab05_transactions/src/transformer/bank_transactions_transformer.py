#!/usr/bin/env python3
import json

from confluent_kafka import Consumer, Producer, TopicPartition

if __name__ == '__main__':
    # todo: the consumer must not commit its offsets!, where should it start?
    consumer_props = {}
    c = Consumer(consumer_props)
    # todo: configure the producer for transactions
    producer_props = {}
    p = Producer(producer_props)
    # Source topic
    TRANSACTIONS_TOPIC = "bank-transactions"
    # target topics
    CREDITS_TOPIC = "credits"
    DEBITS_TOPIC = "debits"

    try:
        # todo read from the source topic
        # todo enable transactions

        # todo: we need some metadata for later. Skip it until you know whatfor
        group_metadata = None
        while True:
            message = c.poll(100)
            if message is None:
                continue
            if message.error():
                print("Consumer error: {}".format(message.error()))
                continue

            # todo get the bank transaction
            transaction = None
            # todo start the Kafka transaction

            suspicious = " <-Suspicious!" if transaction["suspicious"] else ""

            # todo produce to both output topics

            print("{} -> {}: {}€".format(transaction["sender_account"], transaction["receiver_account"],
                                         transaction["amount"]))

            # todo now the producer needs to send the offets to Kafka. How to do so?
            # What do you need for that? Where do you get the information


            p.flush()

            if transaction["suspicious"]:
                # todo abort the transaction
                print("Suspicious transaction between {} and {}! Amount: {}€",
                      transaction["sender_account"], transaction["receiver_account"], transaction["amount"])
            else:
                # todo commit transaction
                pass

    finally:
        # Cleanup
        c.close()
        p.flush()
