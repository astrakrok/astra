package com.example.astraapi.util;

import com.example.astraapi.dto.ErrorResponseDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ErrorUtilsTest {
  @Test
  void shouldWrapErrorMessage() {
    ErrorResponseDto dto = ErrorUtils.create("Sample message");
    assertNotNull(dto);
    assertNotNull(dto.getError());
    assertNotNull(dto.getError().getMessage());
    assertEquals("Sample message", dto.getError().getMessage());
  }
}
