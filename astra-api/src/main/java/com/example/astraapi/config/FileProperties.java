package com.example.astraapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "file")
public class FileProperties {
  private Set<String> allowedExtensions = new HashSet<>();
}
