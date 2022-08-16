package com.example.astraapi.controller;

import com.example.astraapi.dto.MessageDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(Endpoint.NOTIFICATIONS)
@RequiredArgsConstructor
public class NotificationController {
  private final NotificationService notificationService;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void sendMessage(@Valid @ModelAttribute MessageDto messageDto) {
    notificationService.sendMessage(messageDto);
  }
}
