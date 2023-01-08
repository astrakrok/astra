package com.example.astraapi.controller;

import com.example.astraapi.dto.exam.ResponseExamDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Endpoint.SPECIALIZATION_EXAMS)
@RequiredArgsConstructor
public class SpecializationExamController {
  private final ExamService examService;

  @GetMapping
  public List<ResponseExamDto> getActive(@PathVariable("specializationId") Long specializationId) {
    return examService.getActive(specializationId);
  }
}
