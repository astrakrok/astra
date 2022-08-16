package com.example.astraapi.service.impl;

import com.example.astraapi.client.TelegramClient;
import com.example.astraapi.dto.MessageDto;
import com.example.astraapi.service.NotificationService;
import com.example.astraapi.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelegramNotificationService implements NotificationService {
  private final Validator<MessageDto> messageValidator;
  private final TelegramClient telegramClient;

  @Override
  public void sendMessage(MessageDto messageDto) {
    messageValidator.validate(messageDto);
    if (messageDto.getFile() == null) {
      telegramClient.sendRegularMessage(messageDto);
    } else {
      telegramClient.sendPhotoMessage(messageDto);
    }
  }
}
