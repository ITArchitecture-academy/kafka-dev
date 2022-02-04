package example.common;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class JSONDeserializer<T> implements Deserializer<T> {
    private Class<T> destinationClass;
    private Type reflectionTypeToken;

    /* Default constructor needed by Kafka */
    public JSONDeserializer(Class<T> destinationClass) {
        this.destinationClass = destinationClass;
    }

    public JSONDeserializer(Type reflectionTypeToken) {
        this.reflectionTypeToken = reflectionTypeToken;
    }

    private final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null) return null;
        Type type = destinationClass != null ? destinationClass : reflectionTypeToken;
        return gson.fromJson(new String(data, StandardCharsets.UTF_8), type);
    }
}
