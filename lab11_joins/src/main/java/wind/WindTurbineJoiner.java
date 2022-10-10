package wind;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import wind.common.JSONSerde;
import wind.common.KafkaStreamsUtils;
import wind.internal_data_types.CustomerMasterData;
import wind.internal_data_types.WindParkMasterData;
import wind.internal_data_types.WindTurbineDataWithName;
import wind.internal_data_types.WindTurbineMasterData;

import java.util.Properties;

public class WindTurbineJoiner {

    // This is always the same
    public static void main(final String[] args) {
        final Properties settings = new Properties();
        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "wind-turbine-joiner");
        settings.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        settings.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        KafkaStreamsUtils.runKafkaStreamsApp(getTopology(), settings);
    }

    private static Topology getTopology() {
        StreamsBuilder builder = new StreamsBuilder();

        // Task 1: We would like to enrich the data from the `wind-turbine-data` with the names of the wind parks.
        // We find the names for the wind parks in the topic `masterdata-wind_parks` (Imported in Lab 6 by Kafka Connect)
        // The output data type is `WindTurbineDataWithName` from the `internal_data_types` package.

        // As always: Get data from the `wind-turbine-data` topic
        final KStream<String, WindTurbineData> windTurbineData = builder
                .stream("wind-turbine-data",
                        Consumed.with(Serdes.String(), new JSONSerde<>(WindTurbineData.class)));

        // Todo: Create a table from the topic `masterdata-wind_parks` we have imported using Kafka Connect
        // Do not forget about the SerDe. Please check the `internal_data_types` folder for more information
        final KTable<Integer, WindParkMasterData> windParkMasterData = null;

        // Todo: Check whether the Keys in the topics `wind-turbine-data` and `masterdata-wind_parks` are compatible
        //  and whether you can join them directly. Probably you need to do something before joining them.

        // Todo: Now it is time to join the data.
        // Example: stream.join(table, (key, left, right) -> new DataType(left, right),
        //                              Joined.with(KeySerde, LeftSerde, RightSerde)
        final KStream<Integer, WindTurbineDataWithName> windTurbineDataWithName = null;

        // Todo: Write the data to the topic `wind-turbine-data-with-wind-park-names`
        // Do not forget the Serde!

        /*
        Uncomment it only after finishing the first task
        // Todo: Extra Task: Check the `masterdata-wind_turbines` topic. You will see, that it is normalized (i.e. There are only IDs and no names)
        // Create a new topic `wind-turbine-master-data` containing not only the IDs of wind parks and customers, but also their names
        // Use the class `WindTurbineMasterData` for that.
        // Hint: You will need more than one Join for that. We have simplified the `WindTurbineMasterData` class to make it as easy as possible

        // Todo "Import" the data from the `masterdata-customers` topic
        final KTable<Integer, CustomerMasterData> customerMasterData = null;

        // Todo "Import" the data from the `masterdata-wind_turbines` topic
        final KTable<Integer, WindTurbineMasterData> windTurbinesRawMasterData = null;

        // Todo join the windTurbinesRawMasterData with the customers.
        // Hint: Do not forget the Materialized.with
        final KTable<Integer, WindTurbineMasterData> windTurbineRawWithCustomerName = null;
        // Todo join the windTurbineRawWithCustomerName with the windParkMasterData
        final KTable<Integer, WindTurbineMasterData> windTurbineMasterData = null;

        // Todo: write the data to the wind-turbine-master-data topic
        //*/

        return builder.build();
    }
}
