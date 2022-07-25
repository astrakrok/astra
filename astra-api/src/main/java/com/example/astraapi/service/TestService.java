package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestTestDto;

public interface TestService {
  IdDto save(RequestTestDto testDto);
}
