package com.example.astraapi.service.impl;

import com.example.astraapi.model.Change;
import com.example.astraapi.repository.SubjectSpecializationRepository;
import com.example.astraapi.service.ChangeService;
import com.example.astraapi.service.SubjectSpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubjectSpecializationServiceImpl implements SubjectSpecializationService {
  private final SubjectSpecializationRepository repository;
  private final ChangeService changeService;

  @Override
  public void save(Set<Long> specializationIds, Long subjectId) {
    repository.save(specializationIds, subjectId);
  }

  @Override
  @Transactional
  public void updateSpecializationsForSubject(Long subjectId, Set<Long> specializationIds) {
    Set<Long> existingSpecializationIds = repository.getSpecializationIdBySubjectId(subjectId);
    Change<Long> change = changeService.getChange(existingSpecializationIds, specializationIds);
    if (change.getRemoved().size() > 0) {
      repository.delete(change.getRemoved(), subjectId);
    }
    if (change.getAdded().size() > 0) {
      repository.save(change.getAdded(), subjectId);
    }
  }
}
