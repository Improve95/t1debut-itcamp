package ru.improve.itcamp.synthetic.human.core.starter.core.kafka.object;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KafkaWeylandMessage {

    private String methodName;

    private Object[] parameters;

    private Object result;

    private long methodStartAt;

    private long methodEndAt;
}
