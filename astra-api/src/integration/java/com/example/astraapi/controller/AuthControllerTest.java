package com.example.astraapi.controller;

import com.example.astraapi.config.BaseTest;
import com.example.astraapi.dto.UrlDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class AuthControllerTest extends BaseTest {
  @Test
  void shouldReturnGoogleUrl() {
    UrlDto urlDto = webClient.get()
        .uri("/api/v1/auth/oauth2/google")
        .exchange()
        .expectStatus().isEqualTo(HttpStatus.OK)
        .expectBody(UrlDto.class)
        .returnResult()
        .getResponseBody();
    Assertions.assertNotNull(urlDto);
    Assertions.assertNotNull(urlDto.getUrl());
  }
}
