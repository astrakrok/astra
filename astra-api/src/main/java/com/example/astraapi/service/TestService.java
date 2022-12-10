package com.example.astraapi.service;

import com.example.astraapi.dto.*;
import com.example.astraapi.dto.examination.ExaminationSearchDto;
import com.example.astraapi.dto.test.ExaminationTestDto;
import com.example.astraapi.dto.test.RequestTestDto;
import com.example.astraapi.dto.test.TestFullDetailDto;
import com.example.astraapi.dto.test.TestShortDetailDto;
import com.example.astraapi.dto.test.TestingShortTestDto;
import com.example.astraapi.dto.TrainingSearchDto;
import com.example.astraapi.dto.test.TrainingTestDto;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;

import java.util.List;
import java.util.Optional;

public interface TestService {
  IdDto save(RequestTestDto testDto);

  Page<TestShortDetailDto> getAll(Pageable pageable);

  void update(Long id, RequestTestDto testDto);

  Optional<TestFullDetailDto> getDetailedTest(Long id);

  List<TrainingTestDto> getTrainingTests(TrainingSearchDto searchDto);

  List<ExaminationTestDto> getExaminationTests(long count, ExaminationSearchDto searchDto);

  List<ExaminationTestDto> getExaminationTests(List<Long> ids);

  List<TestingShortTestDto> getNotYetSelectedTestingTests(Long testingId);
}
