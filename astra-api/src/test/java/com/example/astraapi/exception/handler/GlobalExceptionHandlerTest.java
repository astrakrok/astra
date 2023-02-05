package com.example.astraapi.exception.handler;

import com.example.astraapi.dto.ErrorResponseDto;
import com.example.astraapi.exception.AlreadyExistsException;
import com.example.astraapi.exception.ValidationException;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.validation.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {
    @InjectMocks
    private GlobalExceptionHandler handler;
    @Mock
    private Logger logger;

    @Test
    void shouldReturnResponseEntityWhenAlreadyExistsException() {
        AlreadyExistsException exception = new AlreadyExistsException("Entity already exists");
        ResponseEntity<ErrorResponseDto> responseEntity = handler.handleException(exception);

        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

    @Test
    void shouldReturnResponseEntityWhenValidationException() {
        ValidationException exception = new ValidationException(new ValidationError(ValidationErrorType.UNKNOWN));
        ResponseEntity<List<ValidationError>> responseEntity = handler.handleException(exception);

        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void shouldReturnResponseEntityWhenGenericException() {
        Exception exception = new Exception();
        ResponseEntity<ErrorResponseDto> responseEntity = handler.handleException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
