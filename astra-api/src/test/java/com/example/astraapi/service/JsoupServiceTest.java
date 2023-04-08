package com.example.astraapi.service;

import com.example.astraapi.service.impl.JsoupServiceImpl;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JsoupServiceTest {
    @InjectMocks
    private JsoupServiceImpl jsoupService;

    @Test
    void shouldReturnDocument() throws IOException {
        Map<String, String> queryParams = Map.of("page", "0");

        Document document = mock(Document.class);

        Connection connection = mock(Connection.class);

        when(connection.get()).thenReturn(document);

        try (MockedStatic<Jsoup> utilities = mockStatic(Jsoup.class)) {
            utilities.when(() -> Jsoup.connect("https://www.example.com/")).thenReturn(connection);

            Document returnedDocument = jsoupService.getDocument("https://www.example.com/", queryParams);
            assertEquals(document, returnedDocument);
        }
    }

    @Test
    void shouldWrapIOExceptionIntoRuntimeException() throws IOException {
        Connection connection = mock(Connection.class);

        when(connection.get()).thenThrow(new IOException());

        try (MockedStatic<Jsoup> utilities = mockStatic(Jsoup.class)) {
            utilities.when(() -> Jsoup.connect("https://www.example.com/")).thenReturn(connection);
            assertThrows(RuntimeException.class, () -> jsoupService.getDocument("https://www.example.com/"));
        }
    }
}
