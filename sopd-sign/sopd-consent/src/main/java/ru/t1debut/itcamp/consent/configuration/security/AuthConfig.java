package ru.t1debut.itcamp.consent.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import ru.t1debut.itcamp.consent.api.exception.CustomAuthEntryPoint;
import ru.t1debut.itcamp.consent.core.security.AuthTokenFilter;
import ru.t1debut.itcamp.consent.core.security.service.AuthService;

import static ru.t1debut.itcamp.consent.api.ApiPath.ACCESS;
import static ru.t1debut.itcamp.consent.api.ApiPath.CONSENTS;
import static ru.t1debut.itcamp.consent.api.ApiPath.CONSENT_STATUS;
import static ru.t1debut.itcamp.consent.api.ApiPath.CONSENT_STATUS_PLACEHOLDER;
import static ru.t1debut.itcamp.consent.api.ApiPath.CONSENT_UUID_PLACEHOLDER;
import static ru.t1debut.itcamp.consent.api.ApiPath.SOPDS;
import static ru.t1debut.itcamp.consent.api.ApiPath.VERSIONS;
import static ru.t1debut.itcamp.consent.api.ApiPath.VERSION_PLACEHOLDER;
import static ru.t1debut.itcamp.consent.util.SecurityUtil.ADMIN_ROLE;
import static ru.t1debut.itcamp.consent.util.SecurityUtil.MANAGER_ROLE;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class AuthConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager AuthenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy(
                ADMIN_ROLE + " > " + MANAGER_ROLE
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthService authService,
            CustomAuthEntryPoint customAuthEntryPoint
    ) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()

                                .requestMatchers(HttpMethod.GET, CONSENTS + CONSENT_UUID_PLACEHOLDER + ACCESS).permitAll()
                                .requestMatchers(
                                        HttpMethod.POST,
                                        CONSENTS + CONSENT_UUID_PLACEHOLDER + CONSENT_STATUS + CONSENT_STATUS_PLACEHOLDER
                                ).permitAll()
                                .requestMatchers(HttpMethod.GET, SOPDS + VERSIONS + VERSION_PLACEHOLDER).permitAll()

                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(conf -> conf
                        .jwt(Customizer.withDefaults())
                        .authenticationEntryPoint(customAuthEntryPoint)
                )
                .addFilterAfter(new AuthTokenFilter(authService), BearerTokenAuthenticationFilter.class);

        return http.build();
    }
}
