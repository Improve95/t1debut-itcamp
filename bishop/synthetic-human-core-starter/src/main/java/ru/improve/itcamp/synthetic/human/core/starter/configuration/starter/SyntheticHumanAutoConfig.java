package ru.improve.itcamp.synthetic.human.core.starter.configuration.starter;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.executor.ExecutorConfig;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties({SyntheticHumanConfig.class, ExecutorConfig.class})
public class SyntheticHumanAutoConfig {

    private final SyntheticHumanConfig syntheticHumanConfig;

    @Bean
    public SyntheticHumanConfig syntheticHumanConfig() {
        return syntheticHumanConfig;
    }
}
