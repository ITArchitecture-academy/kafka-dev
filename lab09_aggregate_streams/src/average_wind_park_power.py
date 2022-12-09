#!/usr/bin/env python3
import faust
from datatypes import *


############## Task: average Wind Park Power
# Now we want to analyze the data of the wind turbines further:
# We want to understand what is the average power of the wind turbines per wind park
# As we cannot implement a total average over an infinite stream, we use the so called sliding average:
# It is defined as sliding_average = sum_so_far/count_so_far
# We implement it using the record SlidingAverage from the datatypes file.

# Here are a few things you need to consider
# 1. Our topic is Keyed by Wind Turbine ID but we want to group by wind park IDs. Thus, we need to rekey the topic.
#    We can do that using the .group_by(Class.property) function in Faust
# 2. We need to store local state (the current sliding average). In Faust (And Kafka Streams) we use tables for that
# 3. We need to print the current average in regular intervals. Lets talk about thet further below

# todo Init the app
app = None

# todo Init the wind turbine topic
wind_turbine_topic = None

# todo create a table where we store the current sliding average.
# You can access the table like a normal dict
average_wind_park_power_table = None


# todo create an agent over the wind turbine topic
async def average_wind_park_power(wind_turbine_data):
    # todo iterate over the data (do not forget the Keys and also the group by!)
    # update the table when you get new data. Use the SlidingAverage.add() function for adding items.
    # If you need to initialize data, use SlidingAverage.init()
    pass

# Print the current average every 2 seconds
@app.timer(interval=2.0)
async def table_printer():
    print("Wind Park\tAverage")
    for key, value in average_wind_park_power_table.items():
        if value is None:
            continue
        print("{}\t{}".format(key, value.average))


if __name__ == '__main__':
    app.main()
