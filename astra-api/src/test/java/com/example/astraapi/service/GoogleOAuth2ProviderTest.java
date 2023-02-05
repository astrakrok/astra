package com.example.astraapi.service;

import com.example.astraapi.config.GoogleProperties;
import com.example.astraapi.dto.UrlDto;
import com.example.astraapi.model.OAuth2UserInfo;
import com.example.astraapi.security.SecurityProperties;
import com.example.astraapi.service.impl.GoogleOAuth2Provider;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class GoogleOAuth2ProviderTest {
    @InjectMocks
    private GoogleOAuth2Provider provider;
    @Mock
    private Base64.Decoder decoder;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private SecurityProperties securityProperties;
    @Spy
    private GoogleProperties googleProperties;

    @BeforeEach
    void beforeEach() {
        Mockito.lenient().when(securityProperties.getIssuerUri()).thenReturn("https://www.example.com/");
        Mockito.lenient().when(securityProperties.getAudience()).thenReturn("exampleAudience");
        Mockito.lenient().when(securityProperties.getClientId()).thenReturn("client-id");
        Mockito.lenient().when(securityProperties.getScope()).thenReturn("openid profile");

        googleProperties.setConnection("google-connection");
        googleProperties.setRedirectUri("http://localhost:3000/google/callback");
        googleProperties.setScope("email profile");
    }

    @Test
    void shouldReturnLoginUrl() {
        UrlDto loginUrl = provider.getLoginUrl();
        String expected = "https://www.example.com/authorize?audience=exampleAudience&access_type=offline&response_type=code&client_id=client-id&connection=google-connection&redirect_uri=http://localhost:3000/google/callback&connection_scope=email profile&scope=openid profile";

        assertEquals(expected, loginUrl.getUrl());
    }

    @Test
    void shouldReturnUserInfo() throws JsonProcessingException {
        Mockito.when(decoder.decode("token")).thenReturn("decoded".getBytes());
        Mockito.when(objectMapper.readValue("decoded", OAuth2UserInfo.class)).thenReturn(new OAuth2UserInfo("Test", "Testovich", "test@gmail.com"));

        OAuth2UserInfo userInfo = provider.getUserInfo("example.token.value");

        assertEquals("Test", userInfo.getGivenName());
        assertEquals("Testovich", userInfo.getFamilyName());
        assertEquals("test@gmail.com", userInfo.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenTokenHasMoreThan3Chunks() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> provider.getUserInfo("invalid.id.token.value"));

        assertEquals("Invalid id token", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTokenHasLessThan3Chunks() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> provider.getUserInfo("id.token"));

        assertEquals("Invalid id token", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenObjectMapperParseException() throws JsonProcessingException {
        Mockito.when(decoder.decode("token")).thenReturn("decoded".getBytes());
        Mockito.when(objectMapper.readValue("decoded", OAuth2UserInfo.class)).thenThrow(new JsonParseException(null, "Message"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> provider.getUserInfo("example.token.value"));

        assertEquals("Unable to parse id token", exception.getMessage());
    }
}
