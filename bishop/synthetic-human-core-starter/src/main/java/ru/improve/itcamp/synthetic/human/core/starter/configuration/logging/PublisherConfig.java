package ru.improve.itcamp.synthetic.human.core.starter.configuration.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.starter.SyntheticHumanConfig;
import ru.improve.itcamp.synthetic.human.core.starter.core.kafka.producer.WeylandProducer;
import ru.improve.itcamp.synthetic.human.core.starter.core.logging.publisher.MethodLoggingPublisher;
import ru.improve.itcamp.synthetic.human.core.starter.core.logging.publisher.PublisherType;
import ru.improve.itcamp.synthetic.human.core.starter.core.logging.publisher.impl.ConsoleMethodLoggingPublisher;
import ru.improve.itcamp.synthetic.human.core.starter.core.logging.publisher.impl.KafkaMethodLoggingPublisher;

import java.util.Map;

@Configuration
public class PublisherConfig {

    private final Map<PublisherType, MethodLoggingPublisher> publisherMap;

    public PublisherConfig(WeylandProducer weylandProducer) {
        this.publisherMap = Map.of(
                PublisherType.KAFKA, new KafkaMethodLoggingPublisher(weylandProducer),
                PublisherType.CONSOLE, new ConsoleMethodLoggingPublisher()
        );
    }

    @Bean
    public MethodLoggingPublisher methodLoggingPublisher(SyntheticHumanConfig syntheticHumanConfig) {
        return publisherMap.get(syntheticHumanConfig.getLogging().getPublisherType());
    }
}
