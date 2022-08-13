package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestTestingDto;
import com.example.astraapi.dto.TestingInfoDto;
import com.example.astraapi.dto.TestingShortTestDto;
import com.example.astraapi.dto.TestingTestQuestionDto;
import com.example.astraapi.dto.TestingWithSpecializationDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.TestingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Endpoint.ADMIN_TESTINGS)
@RequiredArgsConstructor
public class AdminTestingController {
  private final TestingService testingService;

  @PostMapping
  public IdDto save(@Valid @RequestBody RequestTestingDto testingDto) {
    return testingService.save(testingDto);
  }

  @GetMapping("/exams/{examId}")
  public List<TestingWithSpecializationDto> getWithSpecializations(@PathVariable("examId") Long examId) {
    return testingService.getWithSpecializations(examId);
  }

  @GetMapping("/{id}/info")
  public Optional<TestingInfoDto> getTestingInfo(@PathVariable("id") Long id) {
    return testingService.getTestingInfo(id);
  }

  @GetMapping("/{id}/tests")
  public List<TestingTestQuestionDto> getTestsQuestions(@PathVariable("id") Long id) {
    return testingService.getTestsQuestions(id);
  }

  @GetMapping("/{id}/tests/available")
  public List<TestingShortTestDto> getAvailableTestingTests(@PathVariable("id") Long id) {
    return testingService.getNotSelectedTestingTests(id);
  }
}
