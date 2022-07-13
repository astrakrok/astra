package com.example.astraapi.controller;

import com.example.astraapi.config.CorsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {
  private final CorsProperties corsProperties;

  @GetMapping
  public List<List<String>> get() {
    return List.of(
        Arrays.asList(corsProperties.getHeaders()),
        Arrays.asList(corsProperties.getOrigins()),
        Arrays.asList(corsProperties.getHeaders())
    );
  }
}
