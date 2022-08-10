package com.example.astraapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final CorsProperties corsProperties;
  private final SecurityProperties properties;
  private final JwtConverter jwtConverter;

  @Bean
  public JwtDecoder jwtDecoder() {
    NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(properties.getIssuerUri());

    OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(properties.getAudience());
    OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(properties.getIssuerUri());
    OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

    jwtDecoder.setJwtValidator(withAudience);

    return jwtDecoder;
  }

  @Bean
  public CorsConfiguration corsConfigurationSource() {
    final CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(corsProperties.getOrigins());
    configuration.setAllowedMethods(corsProperties.getMethods());
    configuration.setAllowCredentials(true);
    configuration.setAllowedHeaders(corsProperties.getHeaders());
    return configuration;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, CorsConfiguration corsConfiguration) throws Exception {
    return http
        .authorizeHttpRequests(auth -> auth
            .mvcMatchers("/api/v1/auth", "/api/v1/auth/signup").permitAll()
            .mvcMatchers("/api/v1/**").authenticated())
        .csrf().disable()
        .cors().configurationSource(request -> corsConfiguration)
        .and()
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)))
        .build();
  }
}