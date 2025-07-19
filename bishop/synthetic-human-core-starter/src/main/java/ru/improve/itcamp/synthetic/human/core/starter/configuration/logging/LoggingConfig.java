package ru.improve.itcamp.synthetic.human.core.starter.configuration.logging;

import lombok.Value;
import ru.improve.itcamp.synthetic.human.core.starter.core.logging.publisher.PublisherType;

@Value
public class LoggingConfig {

    PublisherType publisherType;
}
