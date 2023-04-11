package com.example.astraapi.mock;

import com.example.astraapi.meta.ExecutionProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Profile(ExecutionProfile.INTEGRATION_TEST)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigMock {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CorsConfiguration corsConfiguration) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf().disable()
                .cors().configurationSource(request -> corsConfiguration)
                .and()
                .httpBasic().disable()
                .build();
    }
}
