package com.example.astraapi.config;

import com.example.astraapi.meta.ConfigProperty;
import com.example.astraapi.security.SecurityProperties;
import com.example.astraapi.validation.ConfigPropertyValidator;
import com.example.astraapi.validation.impl.ExaminationThresholdConfigPropertyValidator;
import com.example.astraapi.validation.impl.TestingConfigPropertyValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class BeanConfigTest {
    @InjectMocks
    private BeanConfig beanConfig;
    @Spy
    private TelegramProperties telegramProperties;
    @Spy
    private SecurityProperties securityProperties;

    @BeforeEach
    void beforeEach() {
        telegramProperties.setBaseUrl("http://localhost:8080/");

        securityProperties.setIssuerUri("http://localhost:8080/");
        securityProperties.setClientId("clientId");
        securityProperties.setClientSecret("clientSecret");
    }

    @Test
    void shouldReturnAuthAPI() {
        assertNotNull(beanConfig.auth());
    }

    @Test
    void shouldReturnConfigPropertyValidatorsMap() {
        Map<ConfigProperty, ConfigPropertyValidator> validators = beanConfig.getConfigPropertyValidators();

        assertTrue(validators.get(ConfigProperty.EXAMINATION_THRESHOLD_PERCENTAGE) instanceof ExaminationThresholdConfigPropertyValidator);
        assertTrue(validators.get(ConfigProperty.TRAINING_DESCRIPTION) instanceof TestingConfigPropertyValidator);
        assertTrue(validators.get(ConfigProperty.EXAMINATION_DESCRIPTION) instanceof TestingConfigPropertyValidator);
        assertTrue(validators.get(ConfigProperty.ADAPTIVE_DESCRIPTION) instanceof TestingConfigPropertyValidator);
    }

    @Test
    void shouldReturnWebClient() {
        try (MockedStatic<WebClient> utilities = mockStatic(WebClient.class)) {
            utilities.when(() -> WebClient.create(any())).thenReturn(mock(WebClient.class));

            assertNotNull(beanConfig.webClient());
        }
    }

    @Test
    void shouldReturnBase64Decoder() {
        assertNotNull(beanConfig.base64Decoder());
    }
}
