package com.example.astraapi.exception.handler;

import com.example.astraapi.dto.ErrorDto;
import com.example.astraapi.dto.ErrorResponseDto;
import com.example.astraapi.exception.AlreadyExistsException;
import com.example.astraapi.exception.ImportException;
import com.example.astraapi.exception.ValidationException;
import com.example.astraapi.meta.ErrorMessage;
import com.example.astraapi.model.validation.ValidationError;
import com.example.astraapi.util.ErrorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleException(AlreadyExistsException exception) {
        ErrorResponseDto error = ErrorUtils.create(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<List<ValidationError>> handleException(ValidationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getErrors());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleException(IllegalArgumentException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(new ErrorDto(exception.getMessage())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleException(MethodArgumentNotValidException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(new ErrorDto(exception.getMessage())));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponseDto> handleException(BindException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(new ErrorDto(exception.getMessage())));
    }

    @ExceptionHandler(ImportException.class)
    public ResponseEntity<List<ValidationError>> handleException(ImportException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(List.of(new ValidationError(exception.getType())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception exception) {
        ErrorResponseDto error = ErrorUtils.create(ErrorMessage.SOMETHING_WENT_WRONG.getMessage());
        log.error("Unexpected error", exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}
