package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestSubjectDto;
import com.example.astraapi.dto.ResponseSubjectDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.SubjectService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(Endpoint.ADMIN_SUBJECTS)
@RequiredArgsConstructor
public class AdminSubjectController {
  private final SubjectService service;

  @PostMapping
  public IdDto saveSubject(@RequestBody @Valid RequestSubjectDto requestSubjectDto) {
    return service.save(requestSubjectDto);
  }

  @GetMapping
  public List<ResponseSubjectDto> getAll() {
    return service.getAll();
  }

  @PutMapping("/{id}")
  public void updateSubject(
      @PathVariable("id") Long id,
      @Valid @RequestBody RequestSubjectDto subjectDto
  ) {
    service.update(id, subjectDto);
  }
}
