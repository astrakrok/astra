package com.example.astraapi.service;

import java.util.Collection;

public interface TestExamService {
  void save(Long testId, Collection<Long> examIds);
}
