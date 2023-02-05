package com.example.astraapi.service;

import com.example.astraapi.client.TelegramClient;
import com.example.astraapi.dto.MessageDto;
import com.example.astraapi.service.impl.TelegramNotificationService;
import com.example.astraapi.validation.impl.MessageValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TelegramNotificationServiceTest {
    @InjectMocks
    private TelegramNotificationService telegramNotificationService;
    @Mock
    private MessageValidator validator;
    @Mock
    private TelegramClient telegramClient;

    @Test
    void shouldThrowExceptionOnInvalidMessage() {
        Mockito.doThrow(new IllegalArgumentException("Invalid message")).when(validator).validate(ArgumentMatchers.any());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> telegramNotificationService.sendMessage(new MessageDto()));

        assertEquals("Invalid message", exception.getMessage());
    }

    @Test
    void shouldSendRegularMessage() {
        MessageDto[] sent = new MessageDto[1];

        Mockito.doAnswer(invocation -> {
            MessageDto messageDto = invocation.getArgument(0);
            sent[0] = messageDto;
            return null;
        }).when(telegramClient).sendRegularMessage(ArgumentMatchers.any());

        telegramNotificationService.sendMessage(new MessageDto("title", "text", null));

        assertNotNull(sent[0]);
    }

    @Test
    void shouldSendPhotoMessage() {
        MessageDto[] sent = new MessageDto[1];

        Mockito.doAnswer(invocation -> {
            MessageDto messageDto = invocation.getArgument(0);
            sent[0] = messageDto;
            return null;
        }).when(telegramClient).sendPhotoMessage(ArgumentMatchers.any());

        telegramNotificationService.sendMessage(new MessageDto("title", "text", Mockito.mock(MultipartFile.class)));

        assertNotNull(sent[0]);
    }
}
