package example.task03_influencers;

import example.task02_international.GreetingSupplier;
import example.task02_international.InternationalGreeting;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;
import java.util.stream.Stream;

public class HelloInfluencer {

    public static void main(final String[] args) {
        final Properties props = new Properties();
        // Same, same…
        // Wir möchten einen eigenen Partitionierer benutzen. Wie machen wir das?
        // ??
        // Wie viele Partitionen für Influencer?
        props.put(InfluencerPartitioner.NUM_INFLUENCER_PARTITIONS, 2);

        final String TOPIC = "greetings_influencer";

        int msgsPerSec = 1;
        if (args.length == 1) {
            msgsPerSec = Integer.parseInt(args[0]);
        }

        // Wir kennen das schon
        final Stream<InternationalGreeting> toGreet = Stream.generate(new GreetingSupplier(msgsPerSec));

        // Selbst erstellen ;)
        Producer producer = null;
        try (producer) {
            toGreet.forEach(greeting -> {
                String key = greeting.receiver_name;
                if (Math.random() < 0.6) { // Wir wollen ja ein paar Influencer ;)
                    key = "Influencer_" + greeting.receiver_name;
                }
                // Und ab die Post
                //producer.send(??);
                System.out.println("Produced greeting for receiver " + greeting.receiver_name);
            });
        }
    }
}
