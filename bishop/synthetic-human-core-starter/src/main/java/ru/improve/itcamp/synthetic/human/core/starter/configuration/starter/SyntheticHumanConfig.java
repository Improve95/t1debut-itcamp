package ru.improve.itcamp.synthetic.human.core.starter.configuration.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.executor.ExecutorConfig;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.kafka.KafkaConfig;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.logging.LoggingConfig;

@Data
@ConfigurationProperties(prefix = "synthetic-human")
public class SyntheticHumanConfig {

    ExecutorConfig executor;

    KafkaConfig kafka;

    LoggingConfig logging;
}
