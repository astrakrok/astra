package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestExamDto;
import com.example.astraapi.dto.ResponseExamDto;
import com.example.astraapi.entity.ExamEntity;
import com.example.astraapi.mapper.ExamMapper;
import com.example.astraapi.repository.ExamRepository;
import com.example.astraapi.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
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
}
