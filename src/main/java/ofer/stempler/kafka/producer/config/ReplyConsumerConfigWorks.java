//package ofer.stempler.kafka.producer.config;
//
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.config.KafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
//import org.springframework.kafka.listener.ContainerProperties;
//import org.springframework.kafka.listener.KafkaMessageListenerContainer;
//import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
////enable this to create consumer
////@Configuration
//public class ReplyConsumerConfigWorks {
//
//
//    public static String KAFKA_BROKERS = "localhost:9092";
//    public static String GROUP_ID = "test_group";
//    public static String REPLY_TOPIC = "BRAIN_1";
//
//    @Bean
//    public ObjectMapper objectMapper(){
//        return new ObjectMapper();
//    }
//
//    //    @Bean
//    //    Producer<Long, Map<String,String>> createProducer() {
//    //        Properties props = new Properties();
//    //        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
//    //        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//    //        //Configure to use costume implementation
//    //        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//    //                ofer.stempler.kafka.producer.serialization.KafkaByteArraySerializer.class);
//    //        //props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getName());
//    //        return new KafkaProducer<Long, Map<String, String>>(props);
//    //    }
//    //
//
//    @Bean
//    public ReplyingKafkaTemplate<String, Map<String, String>, Map<String, String>>
//    replyKafkaTemplate(
//            ProducerFactory<String, Map<String, String>> pf, KafkaMessageListenerContainer<String, Map<String, String>> lc) {
//        ReplyingKafkaTemplate<String, Map<String, String>, Map<String, String>> replyTemplate = new ReplyingKafkaTemplate<>(pf, lc);
//        //        replyTemplate.setReplyTimeout(3000);
//        return replyTemplate;
//    }
//
//    @Bean
//    public Map<String, Object> consumerConfigs() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
//        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
//        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//                ofer.stempler.kafka.producer.serialization.KafkaByteArrayDeserializer.class);
//        return props;
//    }
//
//    Map<String, Object> producerConfigs() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        //Configure to use costume implementation
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//                ofer.stempler.kafka.producer.serialization.KafkaByteArraySerializer.class);
//        //props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getName());
//        return props;
//    }
//
//    @Bean
//    public ProducerFactory<String, Map<String, String>> requestProducerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfigs());
//    }
//
//    @Bean
//    public ConsumerFactory<String,  Map<String, String>> consumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
//    }
//
//    @Bean
//    public KafkaMessageListenerContainer<String, Map<String, String>> kafkaListenerContainerFactory() {
//        ContainerProperties containerProperties = new ContainerProperties(REPLY_TOPIC);
//        return new KafkaMessageListenerContainer<>(consumerFactory(), containerProperties);
//    }
//
//
//
//    //---------------------Consumer------
//    @Bean
//    public ConsumerFactory<String, Map<String, String>> requestConsumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
//    }
//
//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Map<String, String>>> requestListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Map<String, String>> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        factory.setReplyTemplate(replyTemplate());
//        return factory;
//    }
//
//    @Bean
//    public KafkaTemplate<String, Map<String, String>> replyTemplate() {
//        return new KafkaTemplate<>(requestProducerFactory());
//    }
//}
