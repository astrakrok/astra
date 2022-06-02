package com.example.astraapi.controller;

import com.example.astraapi.dto.MessageDto;
import com.example.astraapi.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HelloController {
  private final TestRepository testRepository;

  @GetMapping("/hello")
  public MessageDto hello() {
    return new MessageDto(testRepository.findOne().getName());
  }
}
