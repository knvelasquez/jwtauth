package com.jwtauth.lab.config;

import com.filterlibrary.application.WhiteListService;
import com.filterlibrary.domain.InterceptorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.ArrayList;
import java.util.Arrays;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityFilterChainConfig {
    private static final String AUTHORIZATION_BEARER_TOKEN_INTERCEPTOR = "AuthorizationBearerToken";
    private static final String AUTHORIZATION_BEARER_TOKEN_HANDLER_INTERCEPTOR = "AuthorizationBearerTokenHandlerInterceptor";

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspect) {
        return new MvcRequestMatcher.Builder(introspect);
    }

    @Bean
    public SecurityFilterChain securityFilterChainConfigBean(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                mvc.pattern("/jwt/health"),
                                mvc.pattern("/jwt/superhero.auth"),
                                mvc.pattern("/jwt/auth"),
                                mvc.pattern("/h2-console/**"),
                                mvc.pattern("/api/auth/**"),
                                mvc.pattern("/v3/api-docs.yaml"),
                                mvc.pattern("/v3/api-docs/**"),
                                mvc.pattern("/swagger-ui/**"),
                                mvc.pattern("/swagger-ui.html")
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public WebMvcConfigurer webMvcConfig(InterceptorFactory interceptorFactory) throws Exception {
        return interceptorFactory.from(new ArrayList<>(
                Arrays.asList(AUTHORIZATION_BEARER_TOKEN_INTERCEPTOR))
        );
    }

    @Bean
    public Boolean whiteListConfig(WhiteListService whiteListService) {
        whiteListService.update("/jwt/health", AUTHORIZATION_BEARER_TOKEN_HANDLER_INTERCEPTOR, false);
        whiteListService.update("/jwt/superhero.auth", AUTHORIZATION_BEARER_TOKEN_HANDLER_INTERCEPTOR, false);
        whiteListService.update("/jwt/auth", AUTHORIZATION_BEARER_TOKEN_HANDLER_INTERCEPTOR, false);

        whiteListService.update("/swagger-ui/index.html", AUTHORIZATION_BEARER_TOKEN_HANDLER_INTERCEPTOR, false);
        whiteListService.update("/swagger-ui/swagger-initializer.js", AUTHORIZATION_BEARER_TOKEN_HANDLER_INTERCEPTOR, false);
        whiteListService.update("/swagger-ui/swagger-ui.css", AUTHORIZATION_BEARER_TOKEN_HANDLER_INTERCEPTOR, false);
        whiteListService.update("/swagger-ui/swagger-ui-standalone-preset.js", AUTHORIZATION_BEARER_TOKEN_HANDLER_INTERCEPTOR, false);
        whiteListService.update("/swagger-ui/swagger-ui-bundle.js", AUTHORIZATION_BEARER_TOKEN_HANDLER_INTERCEPTOR, false);
        whiteListService.update("/swagger-ui/index.css", AUTHORIZATION_BEARER_TOKEN_HANDLER_INTERCEPTOR, false);
        whiteListService.update("/swagger-ui/favicon-32x32.png", AUTHORIZATION_BEARER_TOKEN_HANDLER_INTERCEPTOR, false);
        whiteListService.update("/swagger-ui/favicon-16x16.png", AUTHORIZATION_BEARER_TOKEN_HANDLER_INTERCEPTOR, false);
        whiteListService.update("/v3/api-docs/swagger-config", AUTHORIZATION_BEARER_TOKEN_HANDLER_INTERCEPTOR, false);
        whiteListService.update("/v3/api-docs", AUTHORIZATION_BEARER_TOKEN_HANDLER_INTERCEPTOR, false);

        return true;
    }
}
