#!/usr/bin/env python3
import sys
import os
sys.path.append(os.path.abspath('../common'))
from bank_transaction_generator import BankTransactionGenerator
from confluent_kafka import Producer
import json

props = {'bootstrap.servers': 'localhost:9092',
         'partitioner': 'murmur2_random'}
p = Producer(props)
try:
    for transaction in BankTransactionGenerator(1):
        p.produce('bank-transactions', json.dumps(transaction))
        print("{} -> {}: {}€".format(transaction["sender_account"], transaction["receiver_account"], transaction["amount"]))
finally:
    p.flush()
