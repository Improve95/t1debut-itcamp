package ru.improve.itcamp.synthetic.human.core.starter.core.logging.publisher.impl;

import lombok.RequiredArgsConstructor;
import ru.improve.itcamp.synthetic.human.core.starter.core.kafka.object.KafkaWeylandMessage;
import ru.improve.itcamp.synthetic.human.core.starter.core.kafka.producer.WeylandProducer;
import ru.improve.itcamp.synthetic.human.core.starter.core.logging.publisher.MethodLoggingPublisher;
import ru.improve.itcamp.synthetic.human.core.starter.core.logging.publisher.PublisherType;

@RequiredArgsConstructor
public class KafkaMethodLoggingPublisher implements MethodLoggingPublisher {

    private final WeylandProducer weylandProducer;

    @Override
    public void publishLog(String methodName, Object[] args, Object result, long methodStartAt, long methodEndAt) {
        weylandProducer.sendActionLog(
                KafkaWeylandMessage.builder()
                        .methodName(methodName)
                        .parameters(args)
                        .result(result)
                        .methodStartAt(methodStartAt)
                        .methodEndAt(methodEndAt)
                        .build()
        );
    }

    @Override
    public PublisherType getPublisherType() {
        return PublisherType.KAFKA;
    }
}
