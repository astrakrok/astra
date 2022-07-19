package com.example.astraapi.service.impl;

import com.example.astraapi.repository.SubjectSpecializationRepository;
import com.example.astraapi.service.SubjectSpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectSpecializationServiceImpl implements SubjectSpecializationService {
  private final SubjectSpecializationRepository repository;

  @Override
  public void save(List<Long> specializationIds, Long subjectId) {
    repository.save(specializationIds, subjectId);
  }
}
