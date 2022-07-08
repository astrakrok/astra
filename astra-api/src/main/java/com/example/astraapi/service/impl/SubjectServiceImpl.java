package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.SubjectDto;
import com.example.astraapi.entity.SubjectEntity;
import com.example.astraapi.mapper.SubjectMapper;
import com.example.astraapi.repository.SubjectRepository;
import com.example.astraapi.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
  private final SubjectRepository repository;
  private final SubjectMapper mapper;

  @Override
  public IdDto save(Long specializationId, SubjectDto subjectDto) {
    SubjectEntity subjectEntity = mapper.toEntity(specializationId, subjectDto);
    repository.save(subjectEntity);
    return new IdDto(subjectEntity.getId());
  }
}
