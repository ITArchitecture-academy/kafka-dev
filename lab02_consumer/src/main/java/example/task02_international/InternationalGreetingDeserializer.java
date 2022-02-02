package example.task02_international;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

public class InternationalGreetingDeserializer implements Deserializer<InternationalGreeting> {
    private final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    @Override
    public InternationalGreeting deserialize(String topic, byte[] data) {
        if (data == null) return null;
        return gson.fromJson(new String(data, StandardCharsets.UTF_8), InternationalGreeting.class);
    }
}
