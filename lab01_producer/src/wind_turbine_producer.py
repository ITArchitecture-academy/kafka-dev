#!/usr/bin/env python3
from wind_turbine_data_gen import WindTurbineDataGen
from confluent_kafka import Producer
import json

# todo Configure the producer
# How do you make it compatible with the Java producer?
# Please try it out:
# * Produce a few messages with Keys using Python
# * Produce the same messages with the kafka-console-producer.sh
# How do you fix this?
props = {}
# todo Create a producer
p = None
try:
    for data in WindTurbineDataGen(50, 1):
        # todo Produce the data as JSON to the topic `wind-turbine-data`. The Key should be the ID of the wind turbine
        print("Produced data for wind turbine " + data["wind_turbine_id"])
finally:
    # Flush the producer
    p.flush()
