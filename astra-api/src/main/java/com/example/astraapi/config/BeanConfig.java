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

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {
  private final SecurityProperties properties;

  @Bean
  public AuthAPI auth() {
    return new AuthAPI(properties.getIssuerUri(),
        properties.getClientId(),
        properties.getClientSecret());
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
}
