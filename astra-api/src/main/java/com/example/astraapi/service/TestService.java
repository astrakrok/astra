package com.example.astraapi.service;

import com.example.astraapi.dto.TrainingSearchDto;
import com.example.astraapi.dto.examination.ExaminationSearchDto;
import com.example.astraapi.dto.exporting.ExportDto;
import com.example.astraapi.dto.filter.AdminTestFilterDto;
import com.example.astraapi.dto.test.*;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.model.exporting.ExportTest;

import java.util.List;
import java.util.Optional;

public interface TestService {
    TestFullDetailDto save(RequestTestDto testDto);

    TestFullDetailDto saveDraft(RequestTestDto testDto);

    Page<TestShortDetailDto> getAll(AdminTestFilterDto filter, Pageable pageable);

    Optional<TestFullDetailDto> update(Long id, RequestTestDto testDto);

    Optional<TestFullDetailDto> updateDraft(Long id, RequestTestDto testDto);

    Optional<TestFullDetailDto> getDetailedTest(Long id);

    List<TrainingTestDto> getTrainingTests(TrainingSearchDto searchDto);

    List<ExaminationTestDto> getExaminationTests(long count, ExaminationSearchDto searchDto);

    List<ExaminationTestDto> getExaminationTests(List<Long> ids);

    List<TestingShortTestDto> getNotYetSelectedTestingTests(Long testingId);

    void deleteTest(Long id);

    Page<ExportTest> getTestsForExport(ExportDto exportDto, Pageable pageable);
}
