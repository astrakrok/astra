package com.example.astraapi.mock;

import com.example.astraapi.client.TelegramClient;
import com.example.astraapi.dto.MessageDto;
import com.example.astraapi.meta.ExecutionProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(ExecutionProfile.INTEGRATION_TEST)
public class TelegramClientMock implements TelegramClient {
    @Override
    public void sendRegularMessage(MessageDto messageDto) {
    }

    @Override
    public void sendPhotoMessage(MessageDto messageDto) {
    }
}
