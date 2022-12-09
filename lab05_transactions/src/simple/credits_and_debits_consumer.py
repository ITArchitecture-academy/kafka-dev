#!/usr/bin/env python3

from confluent_kafka import Consumer

if __name__ == '__main__':
    # todo How do you explicitly tell the consumer to read aborted transactions?
    props = {'bootstrap.servers': 'localhost:9092',
             'group.id': 'credits-and-debits-consumer',
             'auto.offset.reset': 'earliest'}
    c = Consumer(props)
    try:
        c.subscribe(["credits", "debits"])
        while True:
            message = c.poll(100)
            if message is None:
                continue
            if message.error():
                print("Consumer error: {}".format(message.error()))
                continue
            key = message.key().decode("utf-8")
            data = message.value().decode("utf-8")
            print("{}: {}".format(key, data))
    finally:
        c.close()
