package com.example.astraapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "testing.examination")
public class ExaminationProperties {
  private Integer count;
  private Integer durationInMinutes;
  private Integer finishedAtDeviationSeconds;
}
