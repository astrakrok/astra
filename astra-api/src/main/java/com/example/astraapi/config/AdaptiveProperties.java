package com.example.astraapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "testing.adaptive")
public class AdaptiveProperties {
    private Integer count;
    private Integer subjectsCount;
}
