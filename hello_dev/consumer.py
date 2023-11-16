#!/usr/bin/env python3
from confluent_kafka import Consumer

props = {'bootstrap.servers': 'localhost:9092',
         'group.id': 'notused',
         'enable.auto.commit': False,
         'auto.offset.reset': 'earliest'}
c = Consumer(props)

try:
    c.subscribe(['space_missions'])
    while True:
        message = c.poll(100)
        if message is None:
            continue
        if message.error():
            print("Consumer error: {}".format(message.error()))
            continue
        print("Received Ping: {}".format(message.value().decode('utf-8')))
finally:
    c.close()