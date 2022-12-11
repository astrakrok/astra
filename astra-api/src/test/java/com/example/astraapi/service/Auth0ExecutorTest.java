package com.example.astraapi.service;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.TokenRequest;
import com.example.astraapi.exception.AuthProviderException;
import com.example.astraapi.service.impl.Auth0ExecutorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class Auth0ExecutorTest {
  @InjectMocks
  private Auth0ExecutorImpl executor;

  @Test
  void shouldExecuteRequest() throws Auth0Exception {
    TokenHolder tokenHolder = new TokenHolder("accessToken", "idToken", "refreshToken", "Bearer", 1000000, "email, profile", new Date());
    TokenRequest request = Mockito.mock(TokenRequest.class);
    Mockito.when(request.execute()).thenReturn(tokenHolder);

    TokenHolder answer = executor.execute(request);
    assertEquals("accessToken", answer.getAccessToken());
    assertEquals("idToken", answer.getIdToken());
    assertEquals("refreshToken", answer.getRefreshToken());
    assertEquals("Bearer", answer.getTokenType());
  }

  @Test
  void shouldThrowExceptionWhenExecuteRequest() throws Auth0Exception {
    TokenRequest request = Mockito.mock(TokenRequest.class);
    Mockito.when(request.execute()).thenThrow(new Auth0Exception("Thrown exception"));
    AuthProviderException exception = assertThrows(AuthProviderException.class, () -> executor.execute(request));
    assertEquals("An unexpected error occurred", exception.getMessage());
  }
}
