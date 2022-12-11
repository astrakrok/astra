package com.example.astraapi.exception.handler;

import com.example.astraapi.dto.ErrorResponseDto;
import com.example.astraapi.exception.AlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class AlreadyExistsExceptionHandlerTest {
  @InjectMocks
  private AlreadyExistsExceptionHandler handler;

  @Test
  void shouldReturnResponseEntity() {
    AlreadyExistsException exception = new AlreadyExistsException("Entity already exists");
    ResponseEntity<ErrorResponseDto> responseEntity = handler.handleException(exception);

    assertNotNull(responseEntity.getBody());
    assertEquals("Entity already exists", responseEntity.getBody().getError().getMessage());
    assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
  }
}
