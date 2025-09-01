package ru.t1debut.itcamp.consent.configuration.kafka;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaConfig {

    String bootstrapServers;

    String consentRequestEmailMessageTopic;

    String consentRequestEmailMessageStatusGroupId;

    String consentRequestEmailMessageStatusTopic;
}