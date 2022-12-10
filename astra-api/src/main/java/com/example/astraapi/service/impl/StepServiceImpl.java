package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.step.StepDto;
import com.example.astraapi.entity.StepEntity;
import com.example.astraapi.mapper.StepMapper;
import com.example.astraapi.repository.StepRepository;
import com.example.astraapi.service.StepService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StepServiceImpl implements StepService {
  private final StepRepository stepRepository;
  private final StepMapper stepMapper;

  @Override
  public IdDto save(StepDto stepDto) {
    StepEntity entity = stepMapper.toEntity(stepDto);
    stepRepository.save(entity);
    return new IdDto(entity.getId());
  }

  @Override
  public List<StepDto> getAll() {
    return stepRepository.getAll().stream()
        .map(stepMapper::toDto)
        .collect(Collectors.toList());
  }
}
