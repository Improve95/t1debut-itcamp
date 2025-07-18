package ru.improve.itcamp.synthetic.human.core.starter.configuration.kafka;

import lombok.Value;

@Value
public class KafkaConfig {

    String bootstrapServers;

    String weylandTopic;
}
