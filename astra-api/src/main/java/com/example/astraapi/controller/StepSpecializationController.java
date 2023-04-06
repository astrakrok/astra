package com.example.astraapi.controller;

import com.example.astraapi.dto.specialization.SpecializationDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Endpoint.STEP_SPECIALIZATIONS)
@RequiredArgsConstructor
public class StepSpecializationController {
    private final SpecializationService specializationService;

    @GetMapping
    public ResponseEntity<List<SpecializationDto>> getByStepId(@PathVariable("stepId") Long stepId) {
        List<SpecializationDto> items = specializationService.getAll(stepId);
        return ResponseEntity.ok(items);
    }
}
