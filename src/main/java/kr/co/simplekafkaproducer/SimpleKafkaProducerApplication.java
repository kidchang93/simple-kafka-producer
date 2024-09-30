package kr.co.simplekafkaproducer;

import kr.co.simplekafkaproducer.test.CustomPartitioner;
import kr.co.simplekafkaproducer.test.ProducerCallback;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@Slf4j
@SpringBootApplication
public class SimpleKafkaProducerApplication {
    private final static String TOPIC_NAME = "test";

    private final static String BOOTSTRAP_SERVERS = "172.31.102.28:9092";
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapServers;

    public static void main(String[] args) {
        SpringApplication.run(SimpleKafkaProducerApplication.class, args);

//        String bootstrapServers = "localhost:9092";

        Properties configs = new Properties();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class);

        KafkaProducer<String,String> producer = new KafkaProducer<>(configs);

        String messageValue = "testMessage";
//        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, messageValue);
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, "GASAN-DigitalComplex","ChunjaeEdu");

//        producer.send(record, (metadata, exception) -> {
//            if (exception != null) {
//                log.error("에러다 임마 : ", exception);
//            } else {
//                log.info("보내기 성공 : {}", metadata);
//            }
//        });
        producer.send(record, new ProducerCallback());
        log.info("record = {}", record);
        producer.flush();
        producer.close();

    }


}
