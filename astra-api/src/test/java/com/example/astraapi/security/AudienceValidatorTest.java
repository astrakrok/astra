package com.example.astraapi.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AudienceValidatorTest {
  private AudienceValidator validator;

  @BeforeEach
  void beforeEach() {
    validator = new AudienceValidator("https://www.example.com/audience");
  }

  @Test
  void shouldReturnSuccessResult() {
    Jwt jwt = Mockito.mock(Jwt.class);
    Mockito.when(jwt.getAudience()).thenReturn(List.of("https://www.example.com/audience", "https://www.google.com.ua/"));

    OAuth2TokenValidatorResult result = validator.validate(jwt);
    assertFalse(result.hasErrors());
  }

  @Test
  void shouldReturnFailureResult() {
    Jwt jwt = Mockito.mock(Jwt.class);
    Mockito.when(jwt.getAudience()).thenReturn(List.of("https://www.google.com.ua/"));

    OAuth2TokenValidatorResult result = validator.validate(jwt);
    assertTrue(result.hasErrors());
    assertEquals(1, result.getErrors().size());
    ArrayList<OAuth2Error> errors = new ArrayList<>(result.getErrors());
    assertEquals("invalid_token", errors.get(0).getErrorCode());
  }
}
