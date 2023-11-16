#!/usr/bin/env python3
from confluent_kafka import Producer
import json
import time
import random

# Function to generate random rocket mission data
def create_random_mission_data():
    mission_names = ['Discovery', 'Voyager', 'Pioneer', 'Curiosity', 'New Horizons']
    statuses = ['launched', 'in orbit', 'reached destination', 'transmitting data', 'returning']
    return {
        'mission': random.choice(mission_names),
        'status': random.choice(statuses),
        'timestamp': time.strftime("%Y-%m-%d %H:%M:%S")
    }

# Create a new Kafka Producer
producer = Producer({'bootstrap.servers': 'localhost:9092'})

# Continuously send messages
try:
    while True:
        # Generate a random message
        message = create_random_mission_data()
        message_json = json.dumps(message)

        # Send the message to Kafka
        producer.produce('space_missions', value=message_json)
        print("Produced message: " + message_json)

        # Wait for one second before sending the next message
        time.sleep(1)
except KeyboardInterrupt:
    pass
finally:
    producer.flush()
