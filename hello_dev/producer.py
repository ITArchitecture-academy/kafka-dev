#!/usr/bin/env python3
from confluent_kafka import Producer
import time

# Create a new Kafka Producer
producer = Producer({'bootstrap.servers': 'localhost:9092'})

# List of messages to be sent
messages = [
    "Rocket 1 launched",
    "Rocket 2 in orbit",
    "Rocket 3 reached its destination",
    "Rocket 4 transmitting data",
    "Rocket 5 returning to Earth"
]

# Sending messages
for message in messages:
    producer.produce('space_missions', value=message)
    print("Produced message: " + message)
    time.sleep(2)  # Wait for 2 seconds before sending the next message

producer.flush()
