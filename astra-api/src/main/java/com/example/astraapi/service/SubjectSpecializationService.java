package com.example.astraapi.service;

import java.util.List;

public interface SubjectSpecializationService {
  void save(List<Long> specializationIds, Long subjectId);
}
