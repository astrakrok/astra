package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.test.RequestTestingDetailTestDto;
import com.example.astraapi.dto.test.RequestTestingTestDto;
import com.example.astraapi.dto.testing.TestingDetailDto;

import java.util.List;

public interface TestingTestService {
    IdDto save(RequestTestingTestDto testingTestDto);

    IdDto save(RequestTestingDetailTestDto testingDetailTestDto);

    void delete(Long id);

    void delete(RequestTestingTestDto testingTest);

    List<TestingDetailDto> getTestings(Long testId);
}
