package com.example.astraapi.config;

import com.example.astraapi.meta.OAuth2Connection;
import com.example.astraapi.service.OAuth2Provider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class OAuth2Config {
  private final OAuth2Provider googleOAuth2Provider;
  private final OAuth2Provider facebookOAuth2Provider;

  public OAuth2Config(
      @Qualifier("google") OAuth2Provider googleOAuth2Provider,
      @Qualifier("facebook") OAuth2Provider facebookOAuth2Provider
  ) {
    this.googleOAuth2Provider = googleOAuth2Provider;
    this.facebookOAuth2Provider = facebookOAuth2Provider;
  }

  @Bean
  public Map<OAuth2Connection, OAuth2Provider> oauth2Providers() {
    return new EnumMap<>(
        Map.of(
            OAuth2Connection.FACEBOOK, facebookOAuth2Provider,
            OAuth2Connection.GOOGLE, googleOAuth2Provider
        )
    );
  }
}
