package wind;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class WindAnalyzer {
    public static void main(final String[] args) throws IOException {

        final Properties settings = new Properties();
        //settings.put(??,??)
        // Tip: You have to assign an application id in streams.
        // Tip: We do not have serializers/deserializers but so-called serdes in streams. We work with strings for now
        // Serdes.String().getClass() helps ;)

        // See below
        final Topology topology = getTopology();
        System.out.println("you can paste the topology into this site for a vizualization: https://zz85.github.io/kafka-streams-viz/");
        System.out.println(topology.describe());
        final KafkaStreams streams = new KafkaStreams(topology, settings);
        // We run our Streams application in the background
        final CountDownLatch latch = new CountDownLatch(1);

        //... and wait for a SIGTERM. Then we cleanly close Kafka streams and exit the program
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("<<< Stopping the streams-app Application");
            streams.close();
            latch.countDown();
        }));

        try {
            // Now it's time to start streams
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }


    private static Topology getTopology() {
        StreamsBuilder builder = new StreamsBuilder();
        // We need a so-called source processor.
        // In Kafka Streams we can "connect" to a topic via .stream().
        // We want to read the data from the topic wind-turbine-data
        // Hint: Above, we have configured SerDes for Strings only. But we know, that the data in the
        // `wind-turbine-data`-Topic are `WindTurbineData`s. For that we need to explicitly set the SerDe
        // using `Consumed.with(Serdes.String(), new WindTurbineDataSerDe())`
        // Hint: Check the class `WindTurbineDataSerDe` ;)
        final KStream<String, WindTurbineData> windTurbineData = null; // todo

        // TASK 1: Simple transformations

        // The Power in the topic is given in Kilo-Watts. But we would like to display it in Mega-Watts. How to do that?
        // Hint: You can use the function mapValues
        // Hint2: Use a lambda expression (argument) -> argument * 123
        //                            or: (argument) -> { return argument * 123; }
        final KStream<String, WindTurbineData> dataInMW = null; // todo
        // Print it to STDOUT
        dataInMW.print(Printed.toSysOut());

        // TASK 2: Filtering data
        // Uncomment the code only if you have finished task 1
/*
        // Now we care only about the data if the currentPower is less than 10000 Watts.
        // Hint: Use the function filter
        final KStream<String, WindTurbineData> littleWind = null; // Todo
        // Print it to STDOUT
        littleWind.print(Printed.toSysOut());

        // Now we care only about the data if the currentPower is larger than 10000 Watts.
        final KStream<String, WindTurbineData> muchWind = null; // Todo
        muchWind.print(Printed.toSysOut());
*/
        // Extra TASK 3: Branching

        // The solution from Task 2 is working but not really elegant. Using split() and branch() makes your life easier ;)
        // Hint: Write the data for much wind to the topic `much-wind` and the one for little wind to `little-wind`
        //       Use therefore the function to().
        //       To configure the SerDe use the function Produced.with(Serdes.String(), new WindTurbineDataSerDe()) as the second argument
        // todo

        return builder.build();
    }
}
