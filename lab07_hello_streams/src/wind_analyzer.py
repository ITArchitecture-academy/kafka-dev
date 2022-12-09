#!/usr/bin/env python3
import faust

# todo initialize the Faust App
# You should pass the following arguments:
# * Name of the app
# * our broker(s)
# * serializer for Values (JSON)
# * What to use as a state store (`rocksdb://`)
app = None

# We define the record type that is present in the topic. Use JSON as a (de)serializer
class WindTurbineData(faust.Record, serializer='json'):
    wind_turbine_id: str
    wind_park_id: str
    current_power: float


############## TASK 1: Transforming data

# todo we want to read the data from the `wind-turbine-data`-topic, transform it
#  and then write it back to the topic `wind-turbine-data-in-kw`
# Create the topics for it. Use the record type above for the values

# todo create an agent that reads from the `wind-turbine-topic`.
# There are two ways to write data to a topic:
# 1. either explicitly by `topic.send()`
# 2. or by defining a sink in the agent and use the `yield` keyword
# try out both ways
async def analyze(wind_turbine_data):
    # iterate over the data
    async for data in wind_turbine_data:
        # Divide the current_power by 1000
        data.current_power = data.current_power / 1000
        # todo write the data to the wind-turbine-data-in-kw topic

############## TASK 2: Routing Data
# We read the data again from the wind-turbine-topic and based on the current power we write it either to the topic
# much-wind (when the current power is larger > 10000 W)
# or little-wind (when the current power is smaller than 10000W)

# todo define both topics

# todo define the agent
async def split(wind_turbine_data):
    pass
    # todo iterate over the data (do not forget about keys and values)
    # send the data to the little_wind topic (do not forget about the key! it should stay the same)
    # or to the much_wind topic depending on the current power


if __name__ == '__main__':
    app.main()