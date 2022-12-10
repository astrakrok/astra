package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
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
  private final SpecializationMapper mapper;
  private final SpecializationRepository repository;

  @Override
  public IdDto save(SpecializationDto specializationDto) {
    SpecializationEntity specializationEntity = mapper.toEntity(specializationDto);
    repository.save(specializationEntity);
    return new IdDto(specializationEntity.getId());
  }

  @Override
  public IdDto save(Long stepId, RequestSpecializationDto specializationDto) {
    SpecializationEntity specializationEntity = mapper.toEntity(stepId, specializationDto);
    repository.save(specializationEntity);
    return new IdDto(specializationEntity.getId());
  }

  @Override
  public List<StepSpecializationDto> getAll() {
    return repository.getAllWithSteps().stream()
        .map(mapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<SpecializationDto> getAll(Long stepId) {
    return repository.getAllByStepId(stepId).stream()
        .map(mapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<StepSpecializationDto> getNotSelectedForExam(Long examId) {
    return repository.getNotSelectedByExamId(examId).stream()
        .map(mapper::toDto)
        .collect(Collectors.toList());
  }
}
