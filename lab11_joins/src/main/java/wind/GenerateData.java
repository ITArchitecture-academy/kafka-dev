package wind;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import wind.common.JSONSerializer;
import wind.datatypes.WeatherData;
import wind.datatypes.WeatherDataSupplier;
import wind.datatypes.WindTurbineMasterData;
import wind.datatypes.WindTurbineStatus;

import java.util.Properties;
import java.util.stream.Stream;

public class GenerateData {
    public static void main(String[] args) {
        final Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        // Customers = 10 Random Wind Turbine company names that sound real
        final String[] customers = {"WindTech Solutions", "GaleForce Energy", "AeroPower Systems", "WindMasters", "Turbnaire Innovations", "Zephyr Energy", "WindWorks Corporation", "Vortex Wind Technologies", "AeroGen Power", "GustoWind Enterprises"};
        final String[] windParks = {"NordicWinds Energy Park", "AlpineBreeze Wind Park", "BalticShores Renewables Park", "IberiaGust Wind Park", "DanubeBluff Energy Park", "MediterraneanZephyr Park", "IrishGales Wind Park", "CarpathianBreezes Energy Park", "RhineValley Renewables Park", "AdriaticGust Wind Park",};
        final String[] statuses = {"High Efficiency", "Power Generation", "Grid Connection", "Wind Conditions", "Routine Inspections", "Blade Maintenance", "Energy Production", "Severe Weather", "Turbine Settings", "Optimal Performance",};

        final String masterdataTopic = "wind-turbine-masterdata";
        final String statusTopic = "wind-turbine-status";
        final int NUM_TURBINES = 50;

        var serializer = new JSONSerializer<>();
        Stream<WeatherData> weatherDataStream = Stream.generate(new WeatherDataSupplier());

        try (Producer<String, byte[]> producer = new KafkaProducer<>(props)) {
            for (int i = 0; i < NUM_TURBINES; i++) {
                String randomCustomer = customers[(int) (Math.random() * customers.length)];
                String randomWindPark = windParks[(int) (Math.random() * windParks.length)];


                WindTurbineMasterData windTurbineMasterData = new WindTurbineMasterData(i, randomCustomer, randomWindPark);
                producer.send(new ProducerRecord<>(masterdataTopic, "Turbine" + i,
                        serializer.serialize(masterdataTopic, windTurbineMasterData)));

                // random status
                WindTurbineStatus status = new WindTurbineStatus(i, Math.random() > 0.8, statuses[(int) (Math.random() * statuses.length)]);
                producer.send(new ProducerRecord<>(statusTopic, "Turbine" + i,
                        serializer.serialize(statusTopic, status)));
            }
            System.out.println("Updated Masterdata and Status");
            // Weather data
            weatherDataStream.forEach(weatherData -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                producer.send(new ProducerRecord<>("weather-data", "Turbine" + weatherData.turbineId,
                        serializer.serialize("weather-data", weatherData)));
                System.out.println("Produced weather data for wind turbine " + weatherData.turbineId);
            });
        }
    }
}
