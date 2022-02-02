package example.task03_influencers;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.InvalidRecordException;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.config.ConfigException;

import java.util.List;
import java.util.Map;

public class InfluencerPartitioner implements Partitioner {
    // Idee: Wir reservieren so viele Partitionen für unsere Influencer. "Normal-sterbliche" müssen auf die anderen Partitionen ausweichen
    public static final String NUM_INFLUENCER_PARTITIONS = "NUM_INFLUENCER_PARTITIONS";
    private int numInfluencerPartitions = 4;

    @Override
    public void configure(Map<String, ?> configs) {
        //Wir nutzen die Konfigurationseinstellungen von Kafka
        if (configs.containsKey(NUM_INFLUENCER_PARTITIONS)) {
            numInfluencerPartitions = (int) configs.get(NUM_INFLUENCER_PARTITIONS);
        }
    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // Hier ist die Logik verstekt um zu entscheiden auf welche Partition wir produzieren möchten

        // Zuerst müssen wir uns die Informationen über die Partitionen besorgen. Wie?
        // Tipp: das cluster-Objekt könnte es wissen ;)
        List<PartitionInfo> partitions = null; // todo
        // Wie viele Partitionen haben wir?
        int numPartitions = 0; // todo

        // Wenn es weniger als notwendig sind, dann werfen wir eine Exception!
        if (numPartitions <= numInfluencerPartitions) {
            throw new ConfigException("Topic must have at least " + numInfluencerPartitions + " partitions!");
        }
        // Muss ja ein String sein, weil wir das anhand des Prefixes "Influencers_" entscheiden
        if (!(key instanceof String)) {
            throw new InvalidRecordException("Keys must be Strings!");
        }
        String strKey = (String) key;
        // Auf welche Partition soll das gehen
        int partition;

        if (strKey.startsWith("Influencer_")) {
            // Wie entscheiden wir uns für eine Partition für Influencer?
            // Der Standard-Partitionierer macht folgendes:
            // partition = Math.abs(Utils.murmur2(keyBytes)
            // Achtung: Utils.murmur2 kann auch negative Werte ausgeben, deshalb Math.abs
            // Was müssen wir tun, wenn wir die Daten für die Influencer nur auf die ersten X Partitionen verteilen möchten?
            partition = 0; // so sicherlich nicht ;)
        } else {
            // Und was soll bei Normal-sterblichen passieren?
            partition = 1; // so sicher nicht
        }
        // Vielleicht hilft uns eine Debug-Ausgabe ;)
        System.out.println("Partition " + partition + " <- key " + strKey);
        return partition;
    }

    @Override
    public void close() {
        //Müssen nichts tun
    }

}
