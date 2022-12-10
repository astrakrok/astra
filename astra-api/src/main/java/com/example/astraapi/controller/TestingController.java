package com.example.astraapi.controller;

import com.example.astraapi.dto.testing.TestingDescriptionDto;
import com.example.astraapi.dto.testing.TestingDto;
import com.example.astraapi.dto.testing.TestingWithSpecializationDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.TestingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Endpoint.TESTINGS)
@RequiredArgsConstructor
public class TestingController {
  private final TestingService testingService;

  @GetMapping("/exams/{examId}")
  public List<TestingWithSpecializationDto> getWithSpecializations(@PathVariable("examId") Long examId) {
    return testingService.getWithSpecializations(examId);
  }

  @GetMapping("/description")
  public TestingDescriptionDto getDescription() {
    return testingService.getDescription();
  }

  @GetMapping("/available")
  public List<TestingDto> getAvailableTestings() {
    return testingService.getAvailableTestings();
  }
}
