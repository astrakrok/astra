package com.example.astraapi.controller;

import com.example.astraapi.dto.step.StepDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.StepService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Endpoint.STEPS)
@RequiredArgsConstructor
public class StepController {
    private final StepService stepService;

    @GetMapping
    public List<StepDto> getAll() {
        return stepService.getAll();
    }
}
