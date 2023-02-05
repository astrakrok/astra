package com.example.astraapi.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JsoupUtilsTest {
    @Test
    void shouldReturnDocument() throws IOException {
        Map<String, String> queryParams = Map.of("page", "0");

        Document document = mock(Document.class);

        Connection connection = mock(Connection.class);

        when(connection.get()).thenReturn(document);

        try (MockedStatic<Jsoup> utilities = mockStatic(Jsoup.class)) {
            utilities.when(() -> Jsoup.connect("https://www.example.com/")).thenReturn(connection);

            Document returnedDocument = JsoupUtils.getDocument("https://www.example.com/", queryParams);
            assertEquals(document, returnedDocument);
        }
    }

    @Test
    void shouldWrapIOExceptionIntoRuntimeException() throws IOException {
        Connection connection = mock(Connection.class);

        when(connection.get()).thenThrow(new IOException());

        try (MockedStatic<Jsoup> utilities = mockStatic(Jsoup.class)) {
            utilities.when(() -> Jsoup.connect("https://www.example.com/")).thenReturn(connection);
            assertThrows(RuntimeException.class, () -> JsoupUtils.getDocument("https://www.example.com/"));
        }
    }

    @Test
    void shouldThrowExceptionWhenTryingToInstantiateClass() throws NoSuchMethodException {
        Constructor<JsoupUtils> constructor = JsoupUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Exception exception = assertThrows(Exception.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
    }
}
