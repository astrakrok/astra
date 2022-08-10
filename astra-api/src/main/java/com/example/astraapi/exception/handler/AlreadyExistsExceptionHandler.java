package com.example.astraapi.exception.handler;

import com.example.astraapi.dto.ErrorResponseDto;
import com.example.astraapi.exception.AlreadyExistsException;
import com.example.astraapi.util.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AlreadyExistsExceptionHandler {
  @ExceptionHandler(AlreadyExistsException.class)
  public ResponseEntity<ErrorResponseDto> handleException(AlreadyExistsException exception) {
    ErrorResponseDto error = ErrorUtils.create(exception.getMessage());
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(error);
  }
}
