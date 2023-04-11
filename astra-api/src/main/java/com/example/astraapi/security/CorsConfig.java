package com.example.astraapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {
    private final CorsProperties corsProperties;

    @Bean
    public CorsConfiguration corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(corsProperties.getOrigins());
        configuration.setAllowedMethods(corsProperties.getMethods());
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(corsProperties.getHeaders());
        return configuration;
    }
}
