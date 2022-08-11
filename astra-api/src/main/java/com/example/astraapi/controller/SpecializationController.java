package com.example.astraapi.controller;

import com.example.astraapi.dto.SpecializationDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Endpoint.SPECIALIZATIONS)
@RequiredArgsConstructor
public class SpecializationController {
  private final SpecializationService service;

  @GetMapping
  public List<SpecializationDto> getAll() {
    return service.getAll();
  }
}
