#!/usr/bin/env python3
import json

from confluent_kafka import Consumer

import wind_turbine_api

if __name__ == '__main__':
    # todo Configure the consumer
    # We want to read *all* messages in the topic
    props = {}
    # todo Create the consumer
    c = None
    # We start the API on Port 8989 to display the current Power of the wind turbines
    wind_turbine_api.start()
    try:
        # todo How to tell the consumer from which topic (wind-turbine-data) to consume?

        while True:
            # todo get the message
            message = None
            # the function above does not guarantee us that we get any message back
            if message is None:
                continue
            # There might be an error
            if message.error():
                print("Consumer error: {}".format(message.error()))
                continue
            # todo: Get the key (Kafka knows only byte arrays, but we want a string!)
            key = None
            # todo: Get the value (Kafka knows only byte arrays, but we want a JSON Object!)
            data = None
            # Send the current measurement to the api
            wind_turbine_api.add_measurement(key, data)
            print("{}: {}".format(key, data))
    finally:
        c.close()
