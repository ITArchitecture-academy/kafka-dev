package wind.common;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public class WindSerdes {
    public static Serde<WindTurbineData> WindTurbineDataSerde() {
        return Serdes.serdeFrom(new JSONSerializer<>(), new JSONDeserializer<>(WindTurbineData.class));
    }
}
