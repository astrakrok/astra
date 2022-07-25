package com.example.astraapi.service.impl;

import com.example.astraapi.repository.TestSubjectRepository;
import com.example.astraapi.service.TestSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TestSubjectServiceImpl implements TestSubjectService {
  private final TestSubjectRepository testSubjectRepository;

  @Override
  public void save(Long testId, Collection<Long> subjectIds) {
    testSubjectRepository.save(testId, subjectIds);
  }
}
