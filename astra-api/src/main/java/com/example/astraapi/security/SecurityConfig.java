package com.example.astraapi.security;

import com.example.astraapi.meta.ExecutionProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Profile(ExecutionProfile.PRODUCTION)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
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
    public SecurityFilterChain filterChain(HttpSecurity http, CorsConfiguration corsConfiguration) throws Exception {
        String[] authorized = {"USER", "ADMIN", "SUPER_ADMIN"};
        String[] admin = {"ADMIN", "SUPER_ADMIN"};
        String[] user = {"USER"};
        return http
                .authorizeHttpRequests(auth -> auth
                        .mvcMatchers(
                                "/api/v1/auth",
                                "/api/v1/auth/signup",
                                "/api/v1/auth/password/reset",
                                "/api/v1/auth/oauth2/google",
                                "/api/v1/auth/oauth2/facebook")
                        .permitAll()
                        .mvcMatchers(
                                HttpMethod.GET,
                                "/api/v1/specializations")
                        .permitAll()
                        .mvcMatchers(
                                "/api/v1/properties",
                                "/api/v1/properties/**")
                        .hasAnyAuthority("SUPER_ADMIN")
                        .mvcMatchers(
                                "/api/v1/admin/**")
                        .hasAnyAuthority(admin)
                        .mvcMatchers(
                                HttpMethod.GET,
                                "/api/v1/exams",
                                "/api/v1/specializations",
                                "/api/v1/specializations/**",
                                "/api/v1/steps",
                                "/api/v1/steps/*/specializations",
                                "/api/v1/subjects",
                                "/api/v1/testings/exams/*")
                        .hasAnyAuthority(authorized)
                        .mvcMatchers(
                                "/api/v1/users/current",
                                "/api/v1/users")
                        .hasAnyAuthority(authorized)
                        .mvcMatchers("/api/v1/**")
                        .hasAnyAuthority(user))
                .csrf().disable()
                .cors().configurationSource(request -> corsConfiguration)
                .and()
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)))
                .build();
    }
}
