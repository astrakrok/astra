package com.example.astraapi.service;

import com.example.astraapi.dto.MessageDto;

public interface NotificationService {
  void sendMessage(MessageDto messageDto);
}
