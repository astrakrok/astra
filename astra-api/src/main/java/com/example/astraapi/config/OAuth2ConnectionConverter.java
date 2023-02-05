package com.example.astraapi.config;

import com.example.astraapi.meta.OAuth2Connection;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OAuth2ConnectionConverter implements Converter<String, OAuth2Connection> {
    @Override
    public OAuth2Connection convert(String source) {
        return OAuth2Connection.valueOf(source.strip().toUpperCase());
    }
}
