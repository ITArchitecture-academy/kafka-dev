package wind;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class WindTurbineDataSerializer implements Serializer<WindTurbineData> {
    private final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    @Override
    public byte[] serialize(String topic, WindTurbineData data) {
        return gson.toJson(data).getBytes(StandardCharsets.UTF_8);
    }
}
