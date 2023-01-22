package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.filter.AdminSpecializationFilterDto;
import com.example.astraapi.dto.specialization.RequestSpecializationDto;
import com.example.astraapi.dto.specialization.SpecializationDto;
import com.example.astraapi.dto.specialization.StepSpecializationDto;
import com.example.astraapi.entity.SpecializationEntity;
import com.example.astraapi.mapper.SpecializationMapper;
import com.example.astraapi.repository.SpecializationRepository;
import com.example.astraapi.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {
    private final SpecializationMapper specializationMapper;
    private final SpecializationRepository specializationRepository;

    @Override
    public IdDto save(SpecializationDto specializationDto) {
        SpecializationEntity specializationEntity = specializationMapper.toEntity(specializationDto);
        specializationRepository.save(specializationEntity);
        return new IdDto(specializationEntity.getId());
    }

    @Override
    public IdDto save(Long stepId, RequestSpecializationDto specializationDto) {
        SpecializationEntity specializationEntity = specializationMapper.toEntity(stepId, specializationDto);
        specializationRepository.save(specializationEntity);
        return new IdDto(specializationEntity.getId());
    }

    @Override
    public List<StepSpecializationDto> getAll() {
        return specializationRepository.getAllWithSteps().stream()
                .map(specializationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SpecializationDto> getAll(Long stepId) {
        return specializationRepository.getAllByStepId(stepId).stream()
                .map(specializationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StepSpecializationDto> getNotSelectedForExam(Long examId) {
        return specializationRepository.getNotSelectedByExamId(examId).stream()
                .map(specializationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SpecializationDto> search(AdminSpecializationFilterDto filter) {
        return specializationRepository.search(filter.getStepId(), filter.getTestId()).stream()
                .map(specializationMapper::toDto)
                .collect(Collectors.toList());
    }
}
