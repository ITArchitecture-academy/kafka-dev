package example.common;

import org.apache.kafka.common.serialization.Serdes;

public class JSONSerdes {
    public static class TemperatureReadingSerde extends Serdes.WrapperSerde<TemperatureReading> {
        public TemperatureReadingSerde() {
            super(new JSONSerializer<>(), new JSONDeserializer<>(TemperatureReading.class));
        }
    }

    public static class SlidingAverageSerde extends Serdes.WrapperSerde<SlidingAverage> {
        public SlidingAverageSerde() {
            super(new JSONSerializer<>(), new JSONDeserializer<>(SlidingAverage.class));
        }
    }
}
