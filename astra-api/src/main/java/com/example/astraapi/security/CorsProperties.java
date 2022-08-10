package com.example.astraapi.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
  private List<String> origins;
  private List<String> methods;
  private List<String> headers;
}
