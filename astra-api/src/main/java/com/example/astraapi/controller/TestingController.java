package com.example.astraapi.controller;

import com.example.astraapi.dto.testing.TestingDescriptionDto;
import com.example.astraapi.dto.testing.TestingDetailDto;
import com.example.astraapi.dto.testing.TestingDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.TestingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Endpoint.TESTINGS)
@RequiredArgsConstructor
public class TestingController {
    private final TestingService testingService;

    @GetMapping("/description")
    public TestingDescriptionDto getDescription() {
        return testingService.getDescription();
    }

    @GetMapping("/available")
    public List<TestingDetailDto> getAvailableTestings() {
        return testingService.getAvailableTestings();
    }

    @GetMapping
    public TestingDto getOne(
            @RequestParam("examId") Long examId,
            @RequestParam("specializationId") Long specializationId
    ) {
        return testingService.getOne(examId, specializationId);
    }
}
