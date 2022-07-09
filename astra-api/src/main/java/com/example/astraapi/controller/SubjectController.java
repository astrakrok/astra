package com.example.astraapi.controller;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestSubjectDto;
import com.example.astraapi.dto.ResponseSubjectDto;
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
      @RequestBody RequestSubjectDto requestSubjectDto
  ) {
    return service.save(specializationId, requestSubjectDto);
  }

  @GetMapping
  public List<ResponseSubjectDto> getAllBySpecializationId(@PathVariable("specializationId") Long specializationId) {
    return service.getAllBySpecializationId(specializationId);
  }
}
