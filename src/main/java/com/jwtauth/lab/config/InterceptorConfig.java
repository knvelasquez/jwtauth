package com.jwtauth.lab.config;

import com.filterlibrary.application.WhiteListService;
import com.filterlibrary.domain.InterceptorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
public class InterceptorConfig {
    private static final String AUTHORIZATION_BEARER_TOKEN_INTERCEPTOR = "AuthorizationBearerToken";
    private static final String AUTHORIZATION_BEARER_TOKEN_HANDLER_INTERCEPTOR = "AuthorizationBearerTokenHandlerInterceptor";

    @Bean
    public WebMvcConfigurer webMvcConfig(InterceptorFactory interceptorFactory) throws Exception {
        return interceptorFactory.from(new ArrayList<>(
                Arrays.asList(AUTHORIZATION_BEARER_TOKEN_INTERCEPTOR))
        );
    }

    @Bean
    public Boolean whiteListConfig(WhiteListService whiteListService) {
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
