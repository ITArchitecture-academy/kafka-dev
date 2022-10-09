package wind.extra;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.InvalidRecordException;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;

public class HeavyProducerPartitioner implements Partitioner {
    // Idea: We reserve so many partitions for our turbines producing a lot of data. "Normal mortals" have to switch to the other partitions
    public static final String NUM_HEAVY_PRODUCER_PARTITIONS = "NUM_HEAVY_PRODUCER_PARTITIONS";
    private int numHeavyProducerPartitions = 4;

    @Override
    public void configure(Map<String, ?> configs) {
        // We use the configuration settings of Kafka
        if (configs.containsKey(NUM_HEAVY_PRODUCER_PARTITIONS)) {
            numHeavyProducerPartitions = (int) configs.get(NUM_HEAVY_PRODUCER_PARTITIONS);
        }
    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // Here is the logic to decide on which partition we want to produce.

        // First we need to get the information about the partitions. How?
        // Tip: the cluster object might know it ;)
        List<PartitionInfo> partitions = null; // todo
        int numPartitions = 0; // todo

        // If there are less than necessary, then we throw an exception!
        if (numPartitions <= numHeavyProducerPartitions) {
            throw new ConfigException("Topic must have at least " + numHeavyProducerPartitions + " partitions!");
        }
        // Must be a string, because we decide this based on the prefix "HeavyProducer_".
        if (!(key instanceof String)) {
            throw new InvalidRecordException("Keys must be Strings!");
        }
        String strKey = (String) key;

        int partition;
        if (strKey.startsWith("HeavyProducer_")) {
            // How do we decide on a partition for HeavyProducers?
            // The default partitioner does the following:
            // partition = Math.abs(Utils.murmur2(keyBytes)
            // Attention: Utils.murmur2 can also output negative values, so please use Math.abs
            // What do we have to do if we want to distribute the data for the influencers only to the first X partitions?
            partition = 0; // surely not ;)
        } else {
            // And what should happen with normal data?
            partition = 1; // surely not ;)
        }
        // Maybe a debug output will help us ;)
        //System.out.println("Partition " + partition + " <- key " + strKey);
        return partition;
    }

    @Override
    public void close() {
        // Do not have to do anything
    }

}
