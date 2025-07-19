package ru.improve.itcamp.synthetic.human.core.starter.core.logging.publisher.impl;

import lombok.extern.slf4j.Slf4j;
import ru.improve.itcamp.synthetic.human.core.starter.core.logging.publisher.MethodLoggingPublisher;
import ru.improve.itcamp.synthetic.human.core.starter.core.logging.publisher.PublisherType;

@Slf4j
public class ConsoleMethodLoggingPublisher implements MethodLoggingPublisher {

    @Override
    public void publishLog(String methodName, Object[] args, Object result, long methodStartAt, long methodEndAt) {
        log.info(
                "method was executed with: {}; {}; {}; {}; {}",
                methodName,
                args,
                result,
                methodStartAt,
                methodEndAt
        );
    }

    @Override
    public PublisherType getPublisherType() {
        return PublisherType.CONSOLE;
    }
}
