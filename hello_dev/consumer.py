from kafka import KafkaConsumer
import json

# Create a Kafka Consumer
consumer = KafkaConsumer(
    'space-missions',
    bootstrap_servers='localhost:9092',
    auto_offset_reset='earliest',
    value_deserializer=lambda x: json.loads(x.decode('utf-8')))

# Receive Messages
for message in consumer:
    print(f"Received Message: {message.value}")