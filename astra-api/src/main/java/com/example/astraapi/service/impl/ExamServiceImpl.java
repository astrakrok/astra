package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.exam.RequestExamDto;
import com.example.astraapi.dto.exam.ResponseExamDto;
import com.example.astraapi.dto.specialization.SpecializationDto;
import com.example.astraapi.entity.ExamEntity;
import com.example.astraapi.mapper.ExamMapper;
import com.example.astraapi.repository.ExamRepository;
import com.example.astraapi.service.ExamService;
import com.example.astraapi.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
  private final SpecializationService specializationService;
  private final ExamRepository examRepository;
  private final ExamMapper examMapper;

  @Override
  public IdDto save(RequestExamDto examDto) {
    ExamEntity entity = examMapper.toEntity(examDto);
    examRepository.save(entity);
    return new IdDto(entity.getId());
  }

  @Override
  public List<ResponseExamDto> getAll() {
    return examRepository.getAll().stream()
        .map(examMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public void delete(Long id) {
    examRepository.deleteById(id);
  }

  @Override
  public void update(Long id, RequestExamDto examDto) {
    ExamEntity entity = examMapper.toEntity(examDto);
    examRepository.updateById(id, entity);
  }

  @Override
  public List<SpecializationDto> getAvailableSpecializations(Long id) {
    return specializationService.getNotSelectedForExam(id);
  }
}
