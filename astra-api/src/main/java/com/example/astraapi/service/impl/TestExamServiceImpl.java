package com.example.astraapi.service.impl;

import com.example.astraapi.repository.TestExamRepository;
import com.example.astraapi.service.TestExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TestExamServiceImpl implements TestExamService {
  private final TestExamRepository testExamRepository;

  @Override
  public void save(Long testId, Collection<Long> examIds) {
    testExamRepository.save(testId, examIds);
  }
}
