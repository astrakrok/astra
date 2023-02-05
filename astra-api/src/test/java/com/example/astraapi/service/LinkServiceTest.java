package com.example.astraapi.service;

import com.example.astraapi.meta.LinkSource;
import com.example.astraapi.service.impl.LinkServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class LinkServiceTest {
    @InjectMocks
    private LinkServiceImpl linkService;

    @ParameterizedTest
    @ValueSource(strings = {"https://тестування.укр/example-test", "https://xn--80adi8aaufcj8j.xn--j1amh/"})
    void shouldReturnTestingUkrSource(String value) {
        LinkSource source = linkService.getSource(value);

        assertEquals(LinkSource.TESTING_UKR, source);
    }

    @Test
    void shouldReturnUnknownSource() {
        assertEquals(LinkSource.UNKNOWN, linkService.getSource("https://www.example.com/"));
    }
}
