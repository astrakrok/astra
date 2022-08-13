package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestTestingTestDto;

public interface TestingTestService {
  IdDto save(RequestTestingTestDto testingTestDto);

  void delete(Long id);
}
