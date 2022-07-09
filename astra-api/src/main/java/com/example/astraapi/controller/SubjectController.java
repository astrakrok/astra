package com.example.astraapi.controller;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.SubjectAndSpecializationIdDto;
import com.example.astraapi.dto.SubjectDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.SUBJECTS)
@RequiredArgsConstructor
public class SubjectController {
  private final SubjectService service;

  @PostMapping
  public IdDto saveSubjects(
      @PathVariable("specializationId") Long specializationId,
      @RequestBody SubjectDto subjectDto
  ) {
    return service.save(specializationId, subjectDto);
  }

  @GetMapping
  public List<SubjectAndSpecializationIdDto> getAll(@PathVariable("specializationId") Long specializationId){
    return service.getAll(specializationId);
  }
}
