#!/usr/bin/env python3
import json

from confluent_kafka import Consumer

# todo: implement the same consumer but commit now the offsets automatically every 10s (you do not need to write code for that, librdkafka implements this)