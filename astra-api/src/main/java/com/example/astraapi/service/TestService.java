package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestTestDto;
import com.example.astraapi.dto.TestShortDetailDto;

import java.util.List;

public interface TestService {
  IdDto save(RequestTestDto testDto);

  List<TestShortDetailDto> getAll();
}
