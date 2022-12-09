#!/usr/bin/env python3

from datatypes import *

################### Task 2: Join Tables
# Now we do something, that Kafka Streams does not support!
# We do a three-table Join:
# * masterdata-wind_parks contains the master data for the wind parks
# * masterdata-customers for customers
# * masterdata-wind_turbines for knowing to which wind park and customer a wind turbine belongs

# todo Init the app
app = None

# todo: init the table and topic for  the masterdata-wind_parks topic
# echo '{"id": "0", "name": "wind park 0 name"}' | kcat -b localhost:9092 -t masterdata-wind_parks -k0
wind_park_masterdata_topic = None
wind_park_masterdata_table = None

# todo: init the table and topic for  the masterdata-customers topic. Use CustomerMasterData as the value type
# echo '{"id": "0", "name": "customer 0 name", "price_per_mwh": 123.0}' | kcat -b localhost:9092 -t masterdata-customers -k0
customer_masterdata_topic = None
customer_masterdata_table = None

# todo: init the table and topic for  the masterdata-wind_turbines topic. Use WindTurbineMasterData as the value type
# echo '{"id": "0", "customer_id": 0, "wind_park_id": 0}' | kcat -b localhost:9092 -t masterdata-wind_turbines -k0
wind_turbine_raw_masterdata_topic = None
wind_turbine_raw_masterdata_table = None

# todo: Create an output topic. E.g. wind_turbine_masterdata. Use WindTurbineMasterData as the value type
wind_turbine_masterdata_topic = None

# todo
# This function tries to perform the three-table-join as good as possible.
# Meaning, it looks up the key first in the wind_turbine_raw_masterdata_table.
# If it is not there, we can skip.
# Next it looks it up in the other two tables and completes the raw-masterdata with the information.
# Please note: It is ok if you do not get all the data. Partial results are ok for our use cases
# Finally, send the data to the wind_turbine_masterdata_topic
async def try_masterdata_join(key):
    pass

# todo define three agents that consume the initial masterdata topics
# They should fill the appropriate tables first and then call the `try_masterdata_join` function

async def fill_windpark_masterdata(wind_park_masterdata):
    pass

async def fill_customer_masterdata(customer_masterdata):
    pass

async def fill_wind_turbine_raw_masterdata(wind_turbine_raw_masterdata):
    pass


if __name__ == '__main__':
    app.main()
