package ofer.stempler.kafka.producer.serialization;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

/**
 * KafkaByteArrayDeserializer used to deserialize kafka messages
 *
 */
public class KafkaByteArrayDeserializer<T> implements Deserializer<T> {
    @Override public void configure(Map<String, ?> configs, boolean isKey) {
        // nothing to do
    }

    /**
     * Demoralizes the content
     *
     * @param topic data consumed from topic
     * @param data data to deserialize
     * @return T typed data
     */
    @Override public T deserialize(String topic, byte[] data) {
        return SerializationUtils.deserialize(data);
    }

    @Override public void close() {
        // nothing to do
    }
}
