package com.example.astraapi.service;

import java.util.Set;

public interface SubjectSpecializationService {
  void save(Set<Long> specializationIds, Long subjectId);

  void updateSpecializationsForSubject(Long subjectId, Set<Long> specializationIds);
}
