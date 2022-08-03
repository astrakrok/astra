package com.example.astraapi.controller;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestExamDto;
import com.example.astraapi.dto.ResponseExamDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Endpoint.EXAMS)
@RequiredArgsConstructor
public class ExamController {
  private final ExamService examService;

  @PostMapping
  public IdDto saveExam(@Valid @RequestBody RequestExamDto examDto) {
    return examService.save(examDto);
  }

  @GetMapping
  public List<ResponseExamDto> getAll() {
    return examService.getAll();
  }

  @DeleteMapping("/{id}")
  public void deleteExam(@PathVariable("id") Long id) {
    examService.delete(id);
  }

  @PutMapping("/{id}")
  public void updateExam(
      @PathVariable("id") Long id,
      @Valid @RequestBody RequestExamDto examDto
  ) {
    examService.update(id, examDto);
  }
}
