package com.example.astraapi.controller;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.SpecializationDto;
import com.example.astraapi.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SpecializationController {

  private final SpecializationService service;

  @PostMapping(value = "/specialization")
  public IdDto saveSpecializations(@RequestBody SpecializationDto specializationDto) {
    return service.save(specializationDto);
  }
}
