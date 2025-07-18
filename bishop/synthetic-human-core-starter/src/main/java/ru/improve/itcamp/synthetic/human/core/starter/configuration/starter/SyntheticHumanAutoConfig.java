package ru.improve.itcamp.synthetic.human.core.starter.configuration.starter;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.ExecutorServiceConfig;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(SyntheticHumanProperties.class)
public class SyntheticHumanAutoConfig {

    private final SyntheticHumanProperties syntheticHumanProperties;

    @Bean
    public ExecutorServiceConfig syntheticHumanConfig() {
        return new ExecutorServiceConfig(syntheticHumanProperties);
    }
}
