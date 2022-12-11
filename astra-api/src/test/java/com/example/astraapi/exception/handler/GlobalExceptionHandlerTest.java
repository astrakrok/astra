package com.example.astraapi.exception.handler;

import com.example.astraapi.dto.ErrorResponseDto;
import com.example.astraapi.exception.AlreadyExistsException;
import com.example.astraapi.meta.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {
  @InjectMocks
  private GlobalExceptionHandler handler;
  @Mock
  private Logger logger;

  @Test
  void shouldReturnResponseEntity() {
    AlreadyExistsException exception = new AlreadyExistsException("Entity already exists");
    ResponseEntity<ErrorResponseDto> responseEntity = handler.handleException(exception);

    assertNotNull(responseEntity.getBody());
    assertEquals(ErrorMessage.SOMETHING_WENT_WRONG.getMessage(), responseEntity.getBody().getError().getMessage());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
  }
}
