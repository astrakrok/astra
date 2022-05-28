package com.example.astraapi.controller;

import com.example.astraapi.dto.MessageDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloController {
  @GetMapping("/hello")
  public MessageDto hello() {
    return new MessageDto("Hello, World!!!");
  }
}
