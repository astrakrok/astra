package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.RequestTestDto;
import com.example.astraapi.dto.TestVariantDto;
import com.example.astraapi.entity.TestEntity;
import com.example.astraapi.mapper.TestMapper;
import com.example.astraapi.repository.TestRepository;
import com.example.astraapi.service.TestExamService;
import com.example.astraapi.service.TestService;
import com.example.astraapi.service.TestSubjectService;
import com.example.astraapi.service.TestVariantService;
import com.example.astraapi.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
  private final TestMapper testMapper;
  private final TestRepository testRepository;
  private final Validator<Collection<TestVariantDto>> testVariantsValidator;
  private final TestVariantService testVariantService;
  private final TestExamService testExamService;
  private final TestSubjectService testSubjectService;

  @Transactional
  @Override
  public IdDto save(RequestTestDto testDto) {
    List<TestVariantDto> testVariants = testDto.getVariants();
    testVariantsValidator.validate(testVariants);
    TestEntity entity = testMapper.toEntity(testDto);
    testRepository.save(entity);
    Long testId = entity.getId();
    testVariantService.save(testId, testVariants);
    testExamService.save(testId, testDto.getExamIds());
    testSubjectService.save(testId, testDto.getSubjectIds());
    return new IdDto(entity.getId());
  }
}
