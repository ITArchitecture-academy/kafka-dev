#!/usr/bin/env python3
import json

from confluent_kafka import Consumer

if __name__ == '__main__':
    # todo Configure the consumer
    # Please also disable the automatic commit of offsets here
    props = {}
    # todo Create the consumer
    c = None
    try:
        # todo Consume from the topic wind-turbine-data
        i = 0
        while True:
            # todo get the message
            message = None
            if message is None:
                continue
            if message.error():
                print("Consumer error: {}".format(message.error()))
                continue
            # todo print the messages

            # todo commit the offset
            # Is it a good idea to commit each and every offset?
            # Why or why not?
            # Implement your decision
    finally:
        c.close()
