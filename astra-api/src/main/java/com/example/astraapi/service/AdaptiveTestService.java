package com.example.astraapi.service;

import com.example.astraapi.dto.test.AdaptiveTestDto;

import java.util.List;

public interface AdaptiveTestService {
    List<AdaptiveTestDto> getAdaptiveTests(long specializationId);
}
