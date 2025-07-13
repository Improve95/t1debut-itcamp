package ru.t1camp.improve.kafka.producer.configuration;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaConfig {

    private String bootstrapServers;

    private String weatherTopic;
}
