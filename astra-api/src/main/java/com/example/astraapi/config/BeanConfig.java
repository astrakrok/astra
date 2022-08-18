package com.example.astraapi.config;

import com.auth0.client.auth.AuthAPI;
import com.example.astraapi.meta.ConfigProperty;
import com.example.astraapi.security.SecurityProperties;
import com.example.astraapi.validator.ConfigPropertyValidator;
import com.example.astraapi.validator.impl.ExaminationThresholdConfigPropertyValidator;
import com.example.astraapi.validator.impl.TestingConfigPropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {
  private final TelegramProperties telegramProperties;
  private final SecurityProperties securityProperties;

  @Bean
  public AuthAPI auth() {
    return new AuthAPI(securityProperties.getIssuerUri(),
        securityProperties.getClientId(),
        securityProperties.getClientSecret());
  }

  @Bean
  public Map<ConfigProperty, ConfigPropertyValidator> getConfigPropertyValidators() {
    TestingConfigPropertyValidator testingConfigPropertyValidator = new TestingConfigPropertyValidator();
    return Map.of(
        ConfigProperty.EXAMINATION_THRESHOLD_PERCENTAGE, new ExaminationThresholdConfigPropertyValidator(),
        ConfigProperty.TRAINING_DESCRIPTION, testingConfigPropertyValidator,
        ConfigProperty.EXAMINATION_DESCRIPTION, testingConfigPropertyValidator
    );
  }

  @Bean
  public WebClient webClient() {
    return WebClient.create(telegramProperties.getBaseUrl());
  }

  @Bean
  public Base64.Decoder base64Decoder() {
    return Base64.getUrlDecoder();
  }
}
