package example;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class InternationalGreetingsSerde implements Serde<InternationalGreeting> {
    @Override
    public Serializer<InternationalGreeting> serializer() {
        return null; // todo
    }

    @Override
    public Deserializer<InternationalGreeting> deserializer() {
        return null; // todo
    }
}
