package com.example.astraapi.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

@TestConfiguration
@ComponentScan(basePackages = "com.example.astraapi")
@TestPropertySource("classpath:application.properties")
public class TestConfig {
}
