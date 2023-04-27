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
import com.example.astraapi.model.TestingPage;
import com.example.astraapi.service.TestingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<IdDto> save(@Valid @RequestBody RequestTestingDto testingDto) {
        IdDto idDto = testingService.save(testingDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(idDto);
    }

    @GetMapping("/exams/{examId}")
    public ResponseEntity<List<TestingWithSpecializationDto>> getWithSpecializations(@PathVariable("examId") Long examId) {
        List<TestingWithSpecializationDto> items = testingService.getWithSpecializations(examId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<Optional<TestingInfoDto>> getTestingInfo(@PathVariable("id") Long id) {
        Optional<TestingInfoDto> testing = testingService.getTestingInfo(id);
        return ResponseEntity.ok(testing);
    }

    @PostMapping("/{id}/tests")
    public ResponseEntity<TestingPage<TestingTestQuestionDto>> getTestsQuestions(
            @PathVariable("id") Long id,
            @RequestBody AdminTestingTestsFilterDto filter,
            Pageable pageable
    ) {
        TestingPage<TestingTestQuestionDto> page = testingService.getTestsQuestions(id, filter, pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping("/{id}/tests/available")
    public ResponseEntity<Page<TestingShortTestDto>> getAvailableTestingTests(
            @PathVariable("id") Long id,
            @RequestBody AdminAvailableTestingTestsFilterDto filter,
            Pageable pageable
    ) {
        Page<TestingShortTestDto> page = testingService.getNotSelectedTestingTests(id, filter, pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Optional<TestingInfoDto>> updateStatus(
            @PathVariable("id") Long id,
            @RequestBody TestingStatusDto statusDto
    ) {
        Optional<TestingInfoDto> testing = testingService.updateStatus(id, statusDto);
        return ResponseEntity.ok(testing);
    }
}
