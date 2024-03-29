package com.example.astraapi;

import com.example.astraapi.config.AdaptiveProperties;
import com.example.astraapi.config.ExaminationProperties;
import com.example.astraapi.security.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        CorsProperties.class,
        ExaminationProperties.class,
        AdaptiveProperties.class})
public class AstraApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AstraApiApplication.class, args);
    }
}
