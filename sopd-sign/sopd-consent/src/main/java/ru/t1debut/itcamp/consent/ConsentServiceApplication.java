package ru.t1debut.itcamp.consent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@ConfigurationPropertiesScan
@SpringBootApplication
public class ConsentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsentServiceApplication.class, args);
    }
}
