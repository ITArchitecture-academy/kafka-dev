package example.common;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public class JSONSerdes {
    public static Serde<GreetingTranslation> GreetingTranslationSerde() {
        return Serdes.serdeFrom(new JSONSerializer<>(), new JSONDeserializer<>(GreetingTranslation.class));
    }

    public static Serde<InternationalGreeting> InternationalGreetingSerde() {
        return Serdes.serdeFrom(new JSONSerializer<>(), new JSONDeserializer<>(InternationalGreeting.class));
    }
    public static Serde<NameAndLanguage> NameAndLanguageSerde() {
        return Serdes.serdeFrom(new JSONSerializer<>(), new JSONDeserializer<>(NameAndLanguage.class));
    }
}
