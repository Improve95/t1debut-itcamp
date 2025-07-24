package ru.improve.itcamp.auth.service.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import ru.improve.itcamp.auth.service.api.exception.CustomAuthEntryPoint;
import ru.improve.itcamp.auth.service.core.security.AuthTokenFilter;
import ru.improve.itcamp.auth.service.core.security.service.AuthService;

import static ru.improve.itcamp.auth.service.api.ApiPaths.ACCESS_TOKEN;
import static ru.improve.itcamp.auth.service.api.ApiPaths.AUTH;
import static ru.improve.itcamp.auth.service.api.ApiPaths.LOGIN;
import static ru.improve.itcamp.auth.service.api.ApiPaths.REFRESH;
import static ru.improve.itcamp.auth.service.api.ApiPaths.SIGN_IN;
import static ru.improve.itcamp.auth.service.util.SecurityUtil.ADMIN_ROLE;
import static ru.improve.itcamp.auth.service.util.SecurityUtil.CLIENT_ROLE;
import static ru.improve.itcamp.auth.service.util.SecurityUtil.OPERATOR_ROLE;

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
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy(
                ADMIN_ROLE + " > " + OPERATOR_ROLE + " > " + CLIENT_ROLE
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

                                .requestMatchers(HttpMethod.POST, AUTH + SIGN_IN).permitAll()
                                .requestMatchers(HttpMethod.POST, AUTH + LOGIN).permitAll()
                                .requestMatchers(HttpMethod.POST, AUTH + ACCESS_TOKEN + REFRESH).permitAll()
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
