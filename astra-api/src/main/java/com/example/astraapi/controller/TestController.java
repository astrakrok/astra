package com.example.astraapi.controller;

import com.example.astraapi.dto.TrainingSearchDto;
import com.example.astraapi.dto.test.AdaptiveTestDto;
import com.example.astraapi.dto.test.TrainingTestDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Endpoint.TESTS)
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping("/training")
    public ResponseEntity<List<TrainingTestDto>> getTrainingTesting(@Valid TrainingSearchDto searchDto) {
        List<TrainingTestDto> trainingTests = testService.getTrainingTests(searchDto);
        return ResponseEntity.ok(trainingTests);
    }

    @GetMapping("/adaptive")
    public ResponseEntity<List<AdaptiveTestDto>> getAdaptiveTesting(@RequestParam("specializationId") long specializationId) {
        List<AdaptiveTestDto> adaptiveTests = testService.getAdaptiveTests(specializationId);
        return ResponseEntity.ok(adaptiveTests);
    }
}
