package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestTestDto;
import com.example.astraapi.dto.TestShortDetailDto;
import com.example.astraapi.dto.TrainingTestDto;
import com.example.astraapi.dto.TrainingTestingSearchDto;

import java.util.List;

public interface TestService {
  IdDto save(RequestTestDto testDto);

  List<TestShortDetailDto> getAll();

  List<TrainingTestDto> getTrainingTests(TrainingTestingSearchDto searchDto);
}
