//package ofer.stempler.kafka.producer.send;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Map;
//
//// enable this to consume
//@Service
//@EnableKafka
//public class Consumer {
//    public static final String TOPIC = "way4";
//    public static final String GROUP_ID = "test_group";
//
//
//    //TODO - the topic and the groupID have to be final, we get add them as zk. Should we hard code them?
//    // Maybe we should change this into an interface and have each worker implemnt it with a different topic?
//
//
//    @KafkaListener(topics = TOPIC, groupId = GROUP_ID,  containerFactory = "requestListenerContainerFactory")
//    @SendTo()
//    public Map<String, String> consume(Map<String, String> message) throws IOException {
//
//        System.out.println("Got message:" + message);
//        message.put("Response", "response");
//        return message;
//
//    }
//
//}
