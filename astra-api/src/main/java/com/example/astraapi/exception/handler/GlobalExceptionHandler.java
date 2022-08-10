package com.example.astraapi.exception.handler;

import com.example.astraapi.dto.ErrorResponseDto;
import com.example.astraapi.meta.ErrorMessage;
import com.example.astraapi.util.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> handleException() {
    ErrorResponseDto error = ErrorUtils.create(ErrorMessage.SOMETHING_WENT_WRONG.getMessage());
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(error);
  }
}
