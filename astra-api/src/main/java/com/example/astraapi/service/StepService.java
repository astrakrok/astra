package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.step.StepDto;

import java.util.List;

public interface StepService {
    IdDto save(StepDto stepDto);

    List<StepDto> getAll();
}
