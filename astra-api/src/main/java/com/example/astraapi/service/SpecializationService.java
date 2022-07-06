package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.SpecializationDto;

public interface SpecializationService {
  IdDto save(SpecializationDto specializationDto);
}
