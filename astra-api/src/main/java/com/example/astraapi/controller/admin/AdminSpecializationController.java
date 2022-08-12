package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.SpecializationDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(Endpoint.ADMIN_SPECIALIZATIONS)
@RequiredArgsConstructor
public class AdminSpecializationController {
  private final SpecializationService service;

  @PostMapping
  public IdDto save(@Valid @RequestBody SpecializationDto specializationDto) {
    return service.save(specializationDto);
  }
}
