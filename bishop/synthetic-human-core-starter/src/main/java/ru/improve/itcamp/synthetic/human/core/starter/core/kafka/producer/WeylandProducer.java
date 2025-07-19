package ru.improve.itcamp.synthetic.human.core.starter.core.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.kafka.KafkaConfig;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.starter.SyntheticHumanConfig;
import ru.improve.itcamp.synthetic.human.core.starter.core.kafka.object.KafkaWeylandMessage;

@Slf4j
@Service
public class WeylandProducer {

    private final KafkaConfig kafkaConfig;

    private final KafkaProducer<String, KafkaWeylandMessage> weylandMessageKafkaProducer;

    public WeylandProducer(
            SyntheticHumanConfig syntheticHumanConfig,
            KafkaProducer<String, KafkaWeylandMessage> weylandMessageKafkaProducer
    ) {
        this.kafkaConfig = syntheticHumanConfig.getKafka();
        this.weylandMessageKafkaProducer = weylandMessageKafkaProducer;
    }

    public void sendActionLog(KafkaWeylandMessage kafkaWeylandMessage) {
        weylandMessageKafkaProducer.send(new ProducerRecord<>(kafkaConfig.getWeylandTopic(), kafkaWeylandMessage));
    }
}
