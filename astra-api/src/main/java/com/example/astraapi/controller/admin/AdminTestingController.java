package com.example.astraapi.controller.admin;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.filter.AdminAvailableTestingTestsFilterDto;
import com.example.astraapi.dto.filter.AdminTestingTestsFilterDto;
import com.example.astraapi.dto.test.TestingShortTestDto;
import com.example.astraapi.dto.test.TestingTestQuestionDto;
import com.example.astraapi.dto.testing.RequestTestingDto;
import com.example.astraapi.dto.testing.TestingInfoDto;
import com.example.astraapi.dto.testing.TestingStatusDto;
import com.example.astraapi.dto.testing.TestingWithSpecializationDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.service.TestingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Endpoint.ADMIN_TESTINGS)
@RequiredArgsConstructor
public class AdminTestingController {
    private final TestingService testingService;

    @PostMapping
    public IdDto save(@Valid @RequestBody RequestTestingDto testingDto) {
        return testingService.save(testingDto);
    }

    @GetMapping("/exams/{examId}")
    public List<TestingWithSpecializationDto> getWithSpecializations(@PathVariable("examId") Long examId) {
        return testingService.getWithSpecializations(examId);
    }

    @GetMapping("/{id}/info")
    public Optional<TestingInfoDto> getTestingInfo(@PathVariable("id") Long id) {
        return testingService.getTestingInfo(id);
    }

    @PostMapping("/{id}/tests")
    public Page<TestingTestQuestionDto> getTestsQuestions(
            @PathVariable("id") Long id,
            @RequestBody AdminTestingTestsFilterDto filter,
            Pageable pageable
    ) {
        return testingService.getTestsQuestions(id, filter, pageable);
    }

    @PostMapping("/{id}/tests/available")
    public Page<TestingShortTestDto> getAvailableTestingTests(
            @PathVariable("id") Long id,
            @RequestBody AdminAvailableTestingTestsFilterDto filter,
            Pageable pageable
    ) {
        return testingService.getNotSelectedTestingTests(id, filter, pageable);
    }

    @PutMapping("/{id}/status")
    public Optional<TestingInfoDto> changeStatus(
            @PathVariable("id") Long id,
            @RequestBody TestingStatusDto statusDto
    ) {
        return testingService.changeStatus(id, statusDto);
    }
}
