package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.specialization.RequestSpecializationDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(Endpoint.ADMIN_STEP_SPECIALIZATIONS)
@RequiredArgsConstructor
public class AdminStepSpecializationController {
  private final SpecializationService specializationService;

  @PostMapping
  public IdDto save(
      @PathVariable("stepId") Long stepId,
      @Valid @RequestBody RequestSpecializationDto specializationDto
  ) {
    return specializationService.save(stepId, specializationDto);
  }
}
