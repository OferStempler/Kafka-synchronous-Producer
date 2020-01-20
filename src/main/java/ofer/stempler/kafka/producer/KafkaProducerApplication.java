package ofer.stempler.kafka.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.annotation.EnableKafka;
import ofer.stempler.kafka.producer.send.MyProducer;

@SpringBootApplication
@EnableAutoConfiguration
@EnableKafka
public class KafkaProducerApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(KafkaProducerApplication.class, args);
//        context.getBean(MyProducer.class).produce();
    }

}
