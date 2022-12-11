package com.example.astraapi.service;

import com.auth0.client.auth.AuthAPI;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.TokenRequest;
import com.example.astraapi.security.SecurityProperties;
import com.example.astraapi.service.impl.Auth0ManagementServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class Auth0ManagementServiceTest {
  @InjectMocks
  private Auth0ManagementServiceImpl managementService;
  @Mock
  private AuthAPI auth;
  @Spy
  private SecurityProperties securityProperties;
  @Mock
  private Auth0Executor executor;

  @Test
  void shouldCreateManagementApi() {
    TokenHolder tokenHolder = new TokenHolder("accessToken", "idToken", "refreshToken", "Bearer", 1000000, "email, profile", new Date());
    TokenRequest request = Mockito.mock(TokenRequest.class);
    Mockito.when(auth.requestToken(ArgumentMatchers.any())).thenReturn(request);
    Mockito.when(executor.execute(ArgumentMatchers.any())).thenReturn(tokenHolder);
    Mockito.when(securityProperties.getIssuerUri()).thenReturn("https://www.example.com/");

    assertDoesNotThrow(() -> managementService.newInstance());
  }

  @Test
  void shouldThrowExceptionOnNullDomain() {
    TokenHolder tokenHolder = new TokenHolder("accessToken", "idToken", "refreshToken", "Bearer", 1000000, "email, profile", new Date());
    TokenRequest request = Mockito.mock(TokenRequest.class);
    Mockito.when(auth.requestToken(ArgumentMatchers.any())).thenReturn(request);
    Mockito.when(executor.execute(ArgumentMatchers.any())).thenReturn(tokenHolder);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> managementService.newInstance());
    assertEquals("'domain' cannot be null!", exception.getMessage());
  }
}
