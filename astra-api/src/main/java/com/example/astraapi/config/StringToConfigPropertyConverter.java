package com.example.astraapi.config;

import com.example.astraapi.meta.ConfigProperty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StringToConfigPropertyConverter implements Converter<String, ConfigProperty> {
    @Override
    public ConfigProperty convert(String source) {
        return Arrays.stream(ConfigProperty.values())
                .filter(property -> property.getName().equalsIgnoreCase(source.strip()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such property"));
    }
}
