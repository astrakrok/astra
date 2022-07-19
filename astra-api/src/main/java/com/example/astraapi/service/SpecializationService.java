package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.SpecializationDto;

import javax.validation.Valid;
import java.util.List;

public interface SpecializationService {
  IdDto save(SpecializationDto specializationDto);

  List<SpecializationDto> getAll();
}