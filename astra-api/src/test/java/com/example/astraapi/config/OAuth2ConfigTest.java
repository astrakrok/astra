package com.example.astraapi.config;

import com.example.astraapi.meta.OAuth2Connection;
import com.example.astraapi.service.OAuth2Provider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class OAuth2ConfigTest {
    private OAuth2Config config;
    private final OAuth2Provider googleProvider = mock(OAuth2Provider.class);
    private final OAuth2Provider facebookProvider = mock(OAuth2Provider.class);

    @BeforeEach
    public void beforeEach() {
        config = new OAuth2Config(googleProvider, facebookProvider);
    }

    @Test
    void shouldReturnCorrectOAuth2ProvidersMap() {
        Map<OAuth2Connection, OAuth2Provider> providers = config.oauth2Providers();

        Assertions.assertEquals(googleProvider, providers.get(OAuth2Connection.GOOGLE));
        Assertions.assertEquals(facebookProvider, providers.get(OAuth2Connection.FACEBOOK));
    }
}
