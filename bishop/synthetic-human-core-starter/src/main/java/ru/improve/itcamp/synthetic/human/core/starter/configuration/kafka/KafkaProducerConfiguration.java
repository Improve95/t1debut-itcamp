package ru.improve.itcamp.synthetic.human.core.starter.configuration.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.starter.SyntheticHumanConfig;
import ru.improve.itcamp.synthetic.human.core.starter.core.kafka.object.KafkaWeylandMessage;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class KafkaProducerConfiguration {

    private final KafkaConfig kafkaConfig;

    public KafkaProducerConfiguration(SyntheticHumanConfig syntheticHumanConfig) {
        this.kafkaConfig = syntheticHumanConfig.getKafka();
    }

    //todo отключить создание продюсера если выбрана консоль
    @Bean
    public KafkaProducer<String, KafkaWeylandMessage> weylandMessageKafkaProducer() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        properties.put(JsonSerializer.TYPE_MAPPINGS, "KafkaWeylandMessage:ru.improve.itcamp.synthetic.human.core.starter.core.kafka.object.KafkaWeylandMessage");
        properties.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new KafkaProducer<>(properties);
    }
}
