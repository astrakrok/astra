package com.example.astraapi.validation;

import com.example.astraapi.config.FileProperties;
import com.example.astraapi.dto.MessageDto;
import com.example.astraapi.validation.impl.MessageValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MessageValidatorTest {
    private MessageValidator validator;

    @BeforeEach
    void beforeEach() {
        FileProperties fileProperties = new FileProperties();
        fileProperties.setAllowedExtensions(Set.of("txt", "jpg"));
        validator = new MessageValidator(fileProperties);
    }

    @Test
    void shouldIgnoreAllValidationsOnNullFile() {
        MessageDto message = getMessageMock(null);
        assertDoesNotThrow(() -> validator.validate(message));
    }

    @Test
    void shouldThrowExceptionOnNullFilename() {
        MessageDto message = getMessageMock(getFileMock(null));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(message));
        assertEquals("File name cannot be blank", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionOnInvalidExtension() {
        MessageDto message = getMessageMock(getFileMock("filename.png"));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(message));
        assertEquals("Invalid extension", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionOnFilenameWithoutExtension() {
        MessageDto message = getMessageMock(getFileMock("filename"));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(message));
        assertEquals("Invalid extension", exception.getMessage());
    }

    @Test
    void shouldNotThrowExceptionOnValidMessage() {
        MessageDto message = getMessageMock(getFileMock("filename.txt"));
        assertDoesNotThrow(() -> validator.validate(message));
    }

    private MultipartFile getFileMock(String filename) {
        MultipartFile mock = Mockito.mock(MultipartFile.class);
        Mockito.when(mock.getOriginalFilename()).thenReturn(filename);
        return mock;
    }

    private MessageDto getMessageMock(MultipartFile file) {
        return new MessageDto("title", "text", file);
    }
}
