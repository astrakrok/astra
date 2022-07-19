package com.example.astraapi.controller;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestSubjectDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(Endpoint.SUBJECTS)
@RequiredArgsConstructor
public class SubjectController {
  private final SubjectService service;

  @PostMapping
  public IdDto saveSubject(@RequestBody @Valid RequestSubjectDto requestSubjectDto) {
    return service.save(requestSubjectDto);
  }
}
