package com.example.astraapi.meta;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EndpointTest {
    @Test
    void shouldThrowExceptionWhenTryingToInstantiateClass() throws NoSuchMethodException {
        Constructor<Endpoint> constructor = Endpoint.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Exception exception = assertThrows(Exception.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
    }
}
