from kafka import KafkaProducer
import json

# Create a new Kafka Producer
producer = KafkaProducer(bootstrap_servers='localhost:9092',
                         value_serializer=lambda v: json.dumps(v).encode('utf-8'))

# Nachricht senden
message = {'sender': '<your_name>', 'message': 'A new mission begins!'}
producer.send('space-missions', value=message)
producer.flush()