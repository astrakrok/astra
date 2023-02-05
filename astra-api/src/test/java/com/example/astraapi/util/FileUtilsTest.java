package com.example.astraapi.util;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileUtilsTest {
    @Test
    void shouldReturnFileInputStream() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenReturn(new InputStream() {
            @Override
            public int read() {
                return 0;
            }
        });

        InputStream inputStream = FileUtils.getInputStream(file);

        assertEquals(0, inputStream.read());
    }

    @Test
    void shouldWrapIOExceptionIntoRuntimeException() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenThrow(new IOException());

        assertThrows(RuntimeException.class, () -> FileUtils.getInputStream(file));
    }

    @Test
    void shouldThrowExceptionWhenTryingToInstantiateClass() throws NoSuchMethodException {
        Constructor<FileUtils> constructor = FileUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Exception exception = assertThrows(Exception.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
    }
}
