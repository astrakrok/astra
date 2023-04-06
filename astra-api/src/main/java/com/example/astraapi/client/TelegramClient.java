package com.example.astraapi.client;

import com.example.astraapi.dto.MessageDto;

public interface TelegramClient {
    void sendRegularMessage(MessageDto messageDto);

    void sendPhotoMessage(MessageDto messageDto);
}
