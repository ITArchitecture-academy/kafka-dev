package wind;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

// A SerDe consists simply of a Serializer and a Deserializer put together
public class WindTurbineDataSerDe implements Serde<WindTurbineData> {
    @Override
    public Serializer<WindTurbineData> serializer() {
        return new WindTurbineDataSerializer();
    }

    @Override
    public Deserializer<WindTurbineData> deserializer() {
        return new WindTurbineDataDeserializer();
    }
}

