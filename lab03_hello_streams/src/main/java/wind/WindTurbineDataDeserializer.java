package wind;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

public class WindTurbineDataDeserializer implements Deserializer<WindTurbineData> {
    private final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    @Override
    public WindTurbineData deserialize(String topic, byte[] data) {
        if (data == null) return null;
        return gson.fromJson(new String(data, StandardCharsets.UTF_8), WindTurbineData.class);
    }
}
