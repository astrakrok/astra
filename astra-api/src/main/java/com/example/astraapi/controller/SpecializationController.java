package com.example.astraapi.controller;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.SpecializationDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.SpecializationService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Endpoint.SPECIALIZATIONS)
@RequiredArgsConstructor
public class SpecializationController {
  private final SpecializationService service;

  @PostMapping
  public IdDto saveSpecializations(@Valid @RequestBody SpecializationDto specializationDto) {
    return service.save(specializationDto);
  }

  @GetMapping
  public List<SpecializationDto> getAll() {
    return service.getAll();
  }
}