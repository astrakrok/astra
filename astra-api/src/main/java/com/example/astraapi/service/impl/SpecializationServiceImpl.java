package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.SpecializationDto;
import com.example.astraapi.entity.Specialization;
import com.example.astraapi.mapper.SpecializationMapper;
import com.example.astraapi.repository.SpecializationRepository;
import com.example.astraapi.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {

  private final SpecializationMapper mapper;
  private final SpecializationRepository repository;

  @Override
  public IdDto save(SpecializationDto specializationDto) {
    Specialization specialization = mapper.dtoToModel(specializationDto);
    repository.save(specialization);
    return new IdDto(specialization.getId());
  }
}
