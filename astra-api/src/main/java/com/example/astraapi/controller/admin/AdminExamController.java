package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.exam.RequestExamDto;
import com.example.astraapi.dto.specialization.StepSpecializationDto;
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
@RequestMapping(Endpoint.ADMIN_EXAMS)
@RequiredArgsConstructor
public class AdminExamController {
  private final ExamService examService;

  @PostMapping
  public IdDto save(@Valid @RequestBody RequestExamDto examDto) {
    return examService.save(examDto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") Long id) {
    examService.delete(id);
  }

  @PutMapping("/{id}")
  public void update(
      @PathVariable("id") Long id,
      @Valid @RequestBody RequestExamDto examDto
  ) {
    examService.update(id, examDto);
  }

  @GetMapping("/{id}/specializations/available")
  public List<StepSpecializationDto> getAvailableSpecializations(@PathVariable("id") Long id) {
    return examService.getAvailableSpecializations(id);
  }
}
