package com.example.astraapi.service;

import java.util.Collection;

public interface TestSubjectService {
  void save(Long testId, Collection<Long> subjectIds);
}
