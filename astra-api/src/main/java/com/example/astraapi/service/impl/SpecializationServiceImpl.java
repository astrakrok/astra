package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.SpecializationDto;
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
  public List<SpecializationDto> getAll() {
    List<SpecializationEntity> entities = repository.getAll();
    return entities.stream()
        .map(mapper::toDto)
        .collect(Collectors.toList());
  }
}
