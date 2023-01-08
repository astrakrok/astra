package com.example.astraapi.service.impl;

import com.example.astraapi.dto.FileImportDto;
import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.filter.AdminImportTestFilterDto;
import com.example.astraapi.dto.importing.ImportStatsDto;
import com.example.astraapi.dto.test.RequestTestDto;
import com.example.astraapi.entity.ImportEntity;
import com.example.astraapi.entity.ImportTestEntity;
import com.example.astraapi.entity.projection.ImportSubjectProjection;
import com.example.astraapi.mapper.ImportMapper;
import com.example.astraapi.mapper.TestMapper;
import com.example.astraapi.meta.ValidationErrorType;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import com.example.astraapi.model.importing.ImportResult;
import com.example.astraapi.model.importing.ImportSubject;
import com.example.astraapi.model.importing.ImportSubjectResult;
import com.example.astraapi.model.importing.ImportTest;
import com.example.astraapi.model.validation.ValidationError;
import com.example.astraapi.repository.ImportRepository;
import com.example.astraapi.repository.ImportTestRepository;
import com.example.astraapi.repository.SubjectRepository;
import com.example.astraapi.service.FileImporterFactory;
import com.example.astraapi.service.ImportService;
import com.example.astraapi.service.TestService;
import com.example.astraapi.util.PageUtils;
import com.example.astraapi.validation.ErrorValidator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImportServiceImpl implements ImportService {
    private final FileImporterFactory fileImporterFactory;
    private final TestMapper testMapper;
    private final SubjectRepository subjectRepository;
    private final ImportRepository importRepository;
    private final ImportTestRepository importTestRepository;
    private final ErrorValidator<RequestTestDto> testValidator;
    private final TestService testService;
    private final ImportMapper importMapper;

    @Override
    @Transactional
    public IdDto importFromFile(FileImportDto fileImportDto) {
        ImportResult importResult = fileImporterFactory.get(fileImportDto.getFile().getName()).importTests(fileImportDto.getFile());
        List<ImportTest> tests = importResult.getTests();
        List<ImportTestEntity> importTestEntities = new ArrayList<>();
        ImportEntity importEntity = new ImportEntity(
                null,
                fileImportDto.getTitle(),
                importResult.getSource(),
                importResult.getSourceTitle(),
                Map.of(),
                null);
        importRepository.save(importEntity);
        for (ImportTest test : tests) {
            Map<String, Object> details = new HashMap<>();
            Map<String, List<ImportSubjectProjection>> subjects = subjectRepository.getSubjects(
                            getItems(test.getSubjects(), ImportSubject::getSubjectTitle),
                            getItems(test.getSubjects(), ImportSubject::getSpecializationTitle),
                            getItems(test.getSubjects(), ImportSubject::getStepTitle))
                    .stream()
                    .collect(Collectors.groupingBy(ImportSubjectProjection::getImportSubjectTitle));
            ImportSubjectResult importSubjectResult = getImportSubjectResult(subjects);
            List<ValidationError> errors = getErrorsByResult(importSubjectResult);
            if (!errors.isEmpty()) {
                details.put("errors", errors);
            }
            List<Long> subjectIds = getSubjectIds(importSubjectResult.getValidSubjects());
            RequestTestDto testDto = testMapper.toRequestTestDto(test, subjectIds);
            List<ValidationError> testErrors = testValidator.validate(testDto);
            IdDto idDto = testErrors.isEmpty() ? testService.save(testDto) : testService.saveDraft(testDto);
            importTestEntities.add(new ImportTestEntity(
                    null,
                    importEntity.getId(),
                    idDto.getId(),
                    details));
        }
        importTestRepository.saveAll(importTestEntities);
        return new IdDto(importEntity.getId());
    }

    @Override
    public Page<ImportStatsDto> search(AdminImportTestFilterDto filter, Pageable pageable) {
        return PageUtils.mapPage(
                importRepository.getStats(filter.getSearchText(), pageable),
                importMapper::toDto);
    }

    private List<Long> getSubjectIds(List<ImportSubjectProjection> subjects) {
        return subjects.stream()
                .map(ImportSubjectProjection::getSubjectId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private <T> List<T> getItems(List<ImportSubject> subjects, Function<ImportSubject, T> mapper) {
        return subjects.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    private ImportSubjectResult getImportSubjectResult(Map<String, List<ImportSubjectProjection>> subjects) {
        ImportSubjectResult result = new ImportSubjectResult();
        for (Map.Entry<String, List<ImportSubjectProjection>> subject : subjects.entrySet()) {
            if (subject.getValue().size() > 1) {
                result.getDuplicateSubjects().add(Pair.of(subject));
            } else if (subject.getValue().get(0).getSubjectId() == null) {
                result.getNotFoundSubjects().add(subject.getValue().get(0));
            } else {
                result.getValidSubjects().add(subject.getValue().get(0));
            }
        }
        return result;
    }

    private List<ValidationError> getErrorsByResult(ImportSubjectResult result) {
        ArrayList<ValidationError> errors = new ArrayList<>();
        result.getNotFoundSubjects().stream()
                .map(subject -> new ValidationError(ValidationErrorType.NOT_FOUND, Map.of("subject", subject.getImportSubjectTitle())))
                .forEach(errors::add);
        result.getDuplicateSubjects().stream()
                .map(subject -> new ValidationError(ValidationErrorType.CONFLICT, Map.of(
                        "subject", Map.of(
                                "title", subject.getKey(),
                                "items", subject.getValue()))))
                .forEach(errors::add);
        return errors;
    }
}
