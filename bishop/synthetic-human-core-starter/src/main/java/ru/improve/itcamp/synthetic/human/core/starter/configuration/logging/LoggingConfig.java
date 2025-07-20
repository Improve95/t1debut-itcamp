package ru.improve.itcamp.synthetic.human.core.starter.configuration.logging;

import lombok.Data;
import ru.improve.itcamp.synthetic.human.core.starter.core.logging.publisher.PublisherType;

@Data
public class LoggingConfig {

    PublisherType publisherType = PublisherType.CONSOLE;
}
