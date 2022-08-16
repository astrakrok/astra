package com.example.astraapi.client.impl;

import com.example.astraapi.client.TelegramClient;
import com.example.astraapi.config.TelegramProperties;
import com.example.astraapi.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class TelegramClientImpl implements TelegramClient {
  private final WebClient webClient;
  private final TelegramProperties telegramProperties;

  @Override
  public void sendRegularMessage(MessageDto messageDto) {
    webClient.post()
        .uri(uriBuilder -> getRegularMessageUri(uriBuilder, messageDto))
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public void sendPhotoMessage(MessageDto messageDto) {
    webClient.post()
        .uri(uriBuilder -> getPhotoMessageUri(uriBuilder, messageDto))
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(buildBody(messageDto))
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  private URI getRegularMessageUri(UriBuilder uriBuilder, MessageDto messageDto) {
    String uri = "/bot" + telegramProperties.getApiToken() + "/sendMessage";
    return uriBuilder
        .path(uri)
        .queryParam("parse_mode", "markdown")
        .queryParam("text", buildMessage(messageDto))
        .queryParam("chat_id", telegramProperties.getChatId())
        .build();
  }

  private URI getPhotoMessageUri(UriBuilder uriBuilder, MessageDto messageDto) {
    String url = "/bot" + telegramProperties.getApiToken() + "/sendPhoto";
    return uriBuilder
        .path(url)
        .queryParam("parse_mode", "markdown")
        .queryParam("caption", buildMessage(messageDto))
        .queryParam("chat_id", telegramProperties.getChatId())
        .build();
  }

  private BodyInserters.MultipartInserter buildBody(MessageDto messageDto) {
    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    builder.part("photo", messageDto.getFile().getResource());
    return BodyInserters.fromMultipartData(builder.build());
  }

  private String buildMessage(MessageDto messageDto) {
    return "*" + messageDto.getTitle() + "*\n" + messageDto.getText();
  }
}
