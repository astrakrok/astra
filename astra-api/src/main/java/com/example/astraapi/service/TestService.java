package com.example.astraapi.service;

import com.example.astraapi.dto.ExaminationSearchDto;
import com.example.astraapi.dto.ExaminationTestDto;
import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestTestDto;
import com.example.astraapi.dto.TestFullDetailDto;
import com.example.astraapi.dto.TestShortDetailDto;
import com.example.astraapi.dto.TestingShortTestDto;
import com.example.astraapi.dto.TrainingSearchDto;
import com.example.astraapi.dto.TrainingTestDto;

import java.util.List;
import java.util.Optional;

public interface TestService {
  IdDto save(RequestTestDto testDto);

  void update(Long id, RequestTestDto testDto);

  List<TestShortDetailDto> getAll();

  Optional<TestFullDetailDto> getDetailedTest(Long id);

  List<TrainingTestDto> getTrainingTests(TrainingSearchDto searchDto);

  List<ExaminationTestDto> getExaminationTests(long count, ExaminationSearchDto searchDto);

  List<ExaminationTestDto> getExaminationTests(List<Long> ids);

  List<TestingShortTestDto> getNotYetSelectedTestingTests(Long testingId);
}
