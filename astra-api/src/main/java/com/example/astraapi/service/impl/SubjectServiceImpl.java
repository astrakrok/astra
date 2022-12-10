package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.subject.RequestSubjectDto;
import com.example.astraapi.dto.subject.ResponseSubjectDto;
import com.example.astraapi.entity.SubjectEntity;
import com.example.astraapi.mapper.SubjectMapper;
import com.example.astraapi.repository.SubjectRepository;
import com.example.astraapi.service.SubjectService;
import com.example.astraapi.service.SubjectSpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
  private final SubjectRepository repository;
  private final SubjectMapper mapper;
  private final SubjectSpecializationService service;

  @Override
  @Transactional
  public IdDto save(RequestSubjectDto requestSubjectDto) {
    SubjectEntity subjectEntity = mapper.toEntity(requestSubjectDto);
    repository.save(subjectEntity);
    Long subjectId = subjectEntity.getId();
    service.save(requestSubjectDto.getSpecializationIds(), subjectId);
    return new IdDto(subjectId);
  }

  @Override
  public List<ResponseSubjectDto> getAllSubjectsDetails() {
    return repository.getAllSubjectsDetails().stream()
        .map(mapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void update(Long id, RequestSubjectDto requestSubjectDto) {
    service.updateSpecializationsForSubject(id, requestSubjectDto.getSpecializationIds());
    SubjectEntity subjectEntity = mapper.toEntity(requestSubjectDto);
    repository.update(id, subjectEntity);
  }
}
