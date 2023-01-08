package com.example.astraapi.service.impl;

import com.example.astraapi.model.Change;
import com.example.astraapi.repository.TestSubjectRepository;
import com.example.astraapi.service.ChangeService;
import com.example.astraapi.service.TestSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TestSubjectServiceImpl implements TestSubjectService {
  private final ChangeService changeService;
  private final TestSubjectRepository testSubjectRepository;

  @Override
  public void save(Long testId, Collection<Long> subjectIds) {
    if (subjectIds.isEmpty()) {
      return;
    }
    testSubjectRepository.save(testId, subjectIds);
  }

  @Override
  @Transactional
  public void update(Long testId, Collection<Long> newSubjectsIds) {
    Set<Long> subjectsIds = testSubjectRepository.getSubjectsIdsByTestId(testId);
    Change<Long> change = changeService.getChange(subjectsIds, new HashSet<>(newSubjectsIds));
    if (!change.getRemoved().isEmpty()) {
      testSubjectRepository.delete(testId, change.getRemoved());
    }
    if (!change.getAdded().isEmpty()) {
      testSubjectRepository.save(testId, change.getAdded());
    }
  }
}
