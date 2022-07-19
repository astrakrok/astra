package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestSubjectDto;
import com.example.astraapi.dto.ResponseSubjectDto;
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
    service.save(requestSubjectDto.getSpecializationIds(),subjectEntity.getId());
    return new IdDto(subjectEntity.getId());
  }

  @Override
  public List<ResponseSubjectDto> getAllBySpecializationId(Long specializationId) {
    List<SubjectEntity> entities = repository.getAllBySpecializationId(specializationId);
    return entities.stream()
        .map(mapper::toDto)
        .collect(Collectors.toList());
  }
}
