package example.task02_international;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class InternationalGreetingSerializer implements Serializer<InternationalGreeting> {
    // Wir nutzen Google GSON https://github.com/google/gson
    // für die Serialisierung von JSON
    private final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    @Override
    public byte[] serialize(String topic, InternationalGreeting data) {
        return gson.toJson(data).getBytes(StandardCharsets.UTF_8);
    }
}
