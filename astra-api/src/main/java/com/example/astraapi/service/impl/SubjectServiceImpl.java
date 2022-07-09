package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestSubjectDto;
import com.example.astraapi.dto.ResponseSubjectDto;
import com.example.astraapi.entity.SubjectEntity;
import com.example.astraapi.mapper.SubjectMapper;
import com.example.astraapi.repository.SubjectRepository;
import com.example.astraapi.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
  private final SubjectRepository repository;
  private final SubjectMapper mapper;

  @Override
  public IdDto save(Long specializationId, RequestSubjectDto requestSubjectDto) {
    SubjectEntity subjectEntity = mapper.toEntity(specializationId, requestSubjectDto);
    repository.save(subjectEntity);
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
