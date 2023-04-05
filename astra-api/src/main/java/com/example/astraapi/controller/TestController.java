package com.example.astraapi.controller;

import com.example.astraapi.dto.TrainingSearchDto;
import com.example.astraapi.dto.test.AdaptiveTestDto;
import com.example.astraapi.dto.test.TrainingTestDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.TestService;
import lombok.RequiredArgsConstructor;
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
    public List<TrainingTestDto> getTrainingTesting(@Valid TrainingSearchDto searchDto) {
        return testService.getTrainingTests(searchDto);
    }

    @GetMapping("/adaptive")
    public List<AdaptiveTestDto> getAdaptiveTesting(@RequestParam("specializationId") long specializationId) {
        return testService.getAdaptiveTests(specializationId);
    }
}
