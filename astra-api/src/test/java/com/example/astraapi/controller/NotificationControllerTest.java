package com.example.astraapi.controller;

import com.example.astraapi.dto.MessageDto;
import com.example.astraapi.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class NotificationControllerTest {
    @InjectMocks
    private NotificationController notificationController;
    @Mock
    private NotificationService notificationService;

    @Test
    void shouldReturnCorrectResponseWhenSendMessage() {
        ResponseEntity<Void> response = notificationController.sendMessage(new MessageDto("title", "text", null));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNull(response.getBody());
    }
}
