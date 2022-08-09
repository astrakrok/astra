package com.example.astraapi.config;

import com.auth0.client.auth.AuthAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {
  private final SecurityProperties properties;

  @Bean
  public AuthAPI auth() {
    return new AuthAPI(properties.getDomain(),
        properties.getClientId(),
        properties.getClientSecret());
  }
}
