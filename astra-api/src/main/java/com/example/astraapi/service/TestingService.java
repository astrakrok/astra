package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestTestingDto;
import com.example.astraapi.dto.TestingWithSpecializationDto;

import java.util.List;

public interface TestingService {
  IdDto save(RequestTestingDto testingDto);

  List<TestingWithSpecializationDto> getWithSpecializations(Long examId);
}
