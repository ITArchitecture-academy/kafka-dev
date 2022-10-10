package wind.common;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.lang.reflect.Type;

public class JSONSerde<T> implements Serde<T> {
    private Class<T> destinationClass;
    private Type reflectionTypeToken;

    public JSONSerde(Class<T> destinationClass) {
        this.destinationClass = destinationClass;
    }

    public JSONSerde(Type reflectionTypeToken) {
        this.reflectionTypeToken = reflectionTypeToken;
    }

    @Override
    public Serializer<T> serializer() {
        return new JSONSerializer<>();
    }

    @Override
    public Deserializer<T> deserializer() {
        Type type = destinationClass != null ? destinationClass : reflectionTypeToken;
        return new JSONDeserializer<>(type);
    }
}
