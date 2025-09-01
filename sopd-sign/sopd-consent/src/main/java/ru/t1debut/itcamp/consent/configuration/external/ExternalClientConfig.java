package ru.t1debut.itcamp.consent.configuration.external;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.t1debut.itcamp.consent.core.external.client.service.impl.DefaultExternalAuthClient;

@Configuration
public class ExternalClientConfig {

    @Bean
    public DefaultExternalAuthClient externalAuthClient(ExternalClientDatasource datasource) {
        ExternalClientDatasource.AuthClient authClient = datasource.getAuth();
        return new DefaultExternalAuthClient(authClient.url(), authClient.connectTimeout(), authClient.readTimeout());
    }
}
