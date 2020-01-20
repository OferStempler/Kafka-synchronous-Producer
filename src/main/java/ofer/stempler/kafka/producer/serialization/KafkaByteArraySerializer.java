package ofer.stempler.kafka.producer.serialization;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.serialization.Serializer;

import java.io.Serializable;
import java.util.Map;

/**
 * KafkaByteArraySerializer serializes kafka messages
 *
 */
public class KafkaByteArraySerializer<T extends Serializable> implements Serializer<T> {
    @Override public void configure(Map<String, ?> configs, boolean isKey) {
        // noting todo
    }

    /**
     * Serializes kafka messages
     *
     * @param topic used topic
     * @param data data to serialize
     *
     * @return serialized data as byte[]
     */
    @Override public byte[] serialize(String topic, T data) {
        return SerializationUtils.serialize(data);
    }

    @Override public void close() {
        // noting todo
    }
}
