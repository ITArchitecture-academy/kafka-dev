#!/usr/bin/env python3
import sys
import os
sys.path.append(os.path.abspath('../common'))
from bank_transaction_generator import BankTransactionGenerator
from confluent_kafka import Producer

# todo Configure the producer
# What is required for transactions
props = {}
p = Producer(props)
CREDITS_TOPIC = "credits"
DEBITS_TOPIC = "debits"
try:
    # todo setup the producer for using transactions

    for transaction in BankTransactionGenerator(1):
        p.begin_transaction()
        suspicious = " <-Suspicious!" if transaction["suspicious"] else ""

        # todo produce two messages: one to the credits-topic and one to the debits topic
        # Mark the suspicious transactions by appending the variable above

        print("{} -> {}: {}€".format(transaction["sender_account"], transaction["receiver_account"], transaction["amount"]))
        # For this example we need to flush the messages manually, otherwise we might not see any aborted transactions in the topic
        p.flush()

        if transaction["suspicious"]:
            # todo abort the transaction when it is suspicious

            print("Suspicious transaction between {} and {}! Amount: {}€",
                  transaction["sender_account"], transaction["receiver_account"], transaction["amount"])
        else:
            # todo otherwise commit it
            pass
finally:
    p.flush()
