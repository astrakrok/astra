package com.example.astraapi.util;

import com.example.astraapi.dto.ErrorResponseDto;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ErrorUtilsTest {
  @Test
  void shouldWrapErrorMessage() {
    ErrorResponseDto dto = ErrorUtils.create("Sample message");
    assertNotNull(dto);
    assertNotNull(dto.getError());
    assertNotNull(dto.getError().getMessage());
    assertEquals("Sample message", dto.getError().getMessage());
  }

  @Test
  void shouldThrowExceptionWhenTryingToInstantiateClass() throws NoSuchMethodException {
    Constructor<ErrorUtils> constructor = ErrorUtils.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    Exception exception = assertThrows(Exception.class, constructor::newInstance);
    assertTrue(exception.getCause() instanceof UnsupportedOperationException);
  }
}
