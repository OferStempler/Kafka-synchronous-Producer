package ofer.stempler.kafka.producer.send;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping (value = "/produce/")
public class MyProducer {

    private static String TOPIC_NAME_WAY4 = "way4";
    private static String INTERMEDIATE_TOPIC = "intermediate";
    private static String TOPIC_NAME_RESPONSE = "response";
    public static String REPLY_TOPIC = "BRAIN_1";
//    @Autowired
//    Producer<Long, Map<String,String>> producer;

    @Autowired
    private ReplyingKafkaTemplate<String, Map<String, String>, Map<String, String>> requestReplyKafkaTemplate;

    @Autowired
    private ObjectMapper mapper;

//    @PostConstruct
//    public void init(){
//        mapper = new ObjectMapper();
//    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @PostMapping
    public ResponseEntity <?> produce (@RequestBody  Map<String, Object> request) {
        requestReplyKafkaTemplate.setReplyTimeout(200000);
        //create request
        Map<String, String> message = mapper.convertValue(request, Map.class);
       String requestId = UUID.randomUUID().toString();
        message.put("RequestId", requestId);
        // create producer record
        ProducerRecord<String, Map<String, String>> record =
                new ProducerRecord<String, Map<String, String>>(INTERMEDIATE_TOPIC, message);
        //        new ProducerRecord<Long, Map<String, String>>(TOPIC_NAME_RESPONSE, message);

        // set reply topic in header
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, REPLY_TOPIC.getBytes()));

        // post requst to kafka topic, and synchronously get reply on the specified reply topic
        RequestReplyFuture<String, Map<String, String>, Map<String, String>> sendAndReceive = requestReplyKafkaTemplate
                .sendAndReceive(record);
        sendAndReceive.addCallback(new ListenableFutureCallback<ConsumerRecord<String, Map<String, String>>>() {
            @Override
            public void onFailure(Throwable e) {
                try {
                    System.out.println("ERROR sending message: {}");
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onSuccess(ConsumerRecord<String, Map<String, String>> result) {
                // get consumer record value
                Map<String, String> reply = result.value();
                System.out.println("Reply: " + reply.toString());
            }
        });

        Map<String, String> replay = new HashMap<>();
        try {
            replay = sendAndReceive.get().value();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (!requestId.equals(replay.get("ResponseId"))) {
            System.out.println("ERROR! ids mismatch!");
        }

        return ResponseEntity.ok(replay);
    }

    public String generatId(){
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    }

