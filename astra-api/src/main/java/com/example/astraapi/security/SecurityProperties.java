package com.example.astraapi.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
  private String audience;
  private String issuerUri;
  private String scope;
  private String clientId;
  private String clientSecret;
}
