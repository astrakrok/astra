package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.step.StepDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.StepService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(Endpoint.ADMIN_STEPS)
@RequiredArgsConstructor
public class AdminStepController {
  private final StepService stepService;

  @PostMapping
  public IdDto save(@Valid @RequestBody StepDto stepDto) {
    return stepService.save(stepDto);
  }
}
